package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.serviceexceptions.MagazineDoesNotExist;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalParcelState;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.MagazineRepository;
import com.github.haseoo.courier.repositories.ports.ParcelRepository;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.EmployeeType.COURIER;
import static com.github.haseoo.courier.enums.ParcelStateType.*;
import static com.github.haseoo.courier.utilities.Constants.PARCEL_DEFAULT_WEEKS_TO_DELIVERY_AFTER_ADD_TO_MAGAZINE;

@Service
@RequiredArgsConstructor
public class ParcelStateServiceImpl implements ParcelStateService {
    private final MagazineRepository magazineRepository;
    private final CourierRepository courierRepository;
    private final ParcelRepository parcelRepository;

    @Override
    @Transactional
    public MagazineData addParcelsToMagazine(Long magazineId, List<Long> parcelIds) {
        MagazineModel magazineModel = magazineRepository.getById(magazineId).orElseThrow(() -> new MagazineDoesNotExist(magazineId));
        List<ParcelModel> parcelModels = getParcelModels(parcelIds);
        List<ParcelStateRecord> parcelStateRecords = getParcelStateRecords(magazineModel, parcelModels);
        magazineModel.getParcelStates().addAll(parcelStateRecords);
        magazineModel = magazineRepository.saveAndFlush(magazineModel);
        setExpectedDeliveryTimeNextWeek(parcelModels);
        return MagazineData.of(magazineModel);
    }

    @Override
    @Transactional
    public CourierData assignParcelsToCourier(Long courierId, List<Long> parcelIds) {
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        List<ParcelStateRecord> parcelStateRecords = getParcelStateRecords(courierModel, getParcelModels(parcelIds), ASSIGNED);
        courierModel.getParcelStates().addAll(parcelStateRecords);
        return CourierData.of(courierRepository.saveAndFlush(courierModel));
    }

    @Override
    @Transactional
    public CourierData setAsPickedByCourier(Long courierId, List<Long> parcelIds) {
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        List<ParcelModel> parcelModels = getParcelModels(parcelIds);
        List<ParcelStateRecord> parcelStateRecords = getParcelStateRecords(courierModel, parcelModels, AT_COURIER);
        courierModel.getParcelStates().addAll(parcelStateRecords);
        setExpectedDeliveryTimeToday(parcelModels);
        return CourierData.of(courierRepository.saveAndFlush(courierModel));
    }

    @Override
    @Transactional
    public ParcelData setParcelAsDelivered(Long courierId, Long parcelId) {
        return changeOneParcelState(courierId, parcelId, DELIVERED);
    }

    @Override
    @Transactional
    public ParcelData setParcelReturned(Long courierId, Long parcelId) {
        return changeOneParcelState(courierId, parcelId, RETURNED);
    }

    private ParcelData changeOneParcelState(Long courierId, Long parcelId, ParcelStateType newState) {
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        ParcelModel parcelModel = parcelRepository.getById(parcelId).orElseThrow(() -> new ParcelNotFound(parcelId));
        verifyState(parcelModel, AT_COURIER);
        ParcelStateRecord parcelStateRecord = prepareParcelStateRecord(parcelModel, courierModel, newState);
        parcelModel.getParcelStates().add(parcelStateRecord);
        return ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
    }

    private ParcelStateRecord prepareParcelStateRecord(ParcelModel parcelModel, CourierModel courierModel, ParcelStateType parcelStateType) {
        if (parcelStateType == ASSIGNED) {
            verifyState(parcelModel, AT_SENDER, IN_MAGAZINE);
        }
        if (parcelStateType == AT_COURIER) {
            verifyState(parcelModel, ASSIGNED);
        }
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        parcelStateRecord.setParcel(parcelModel);
        parcelStateRecord.setCourier(courierModel);
        parcelStateRecord.setState(parcelStateType);
        return parcelStateRecord;
    }

    private ParcelStateRecord prepareParcelStateRecord(ParcelModel parcelModel, MagazineModel magazineModel) {
        verifyState(parcelModel, AT_COURIER);
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        parcelStateRecord.setParcel(parcelModel);
        parcelStateRecord.setMagazine(magazineModel);
        parcelStateRecord.setState(IN_MAGAZINE);
        return parcelStateRecord;
    }

    private void setExpectedDeliveryTimeNextWeek(List<ParcelModel> parcelModels) {
        parcelModels.forEach(parcelModel -> {
            parcelModel.setExpectedDeliveryTime(LocalDate.now().plusWeeks(PARCEL_DEFAULT_WEEKS_TO_DELIVERY_AFTER_ADD_TO_MAGAZINE));
            parcelRepository.saveAndFlush(parcelModel);
        });
    }

    private void setExpectedDeliveryTimeToday(List<ParcelModel> parcelModels) {
        parcelModels.forEach(parcelModel -> {
            parcelModel.setExpectedDeliveryTime(LocalDate.now());
            parcelRepository.saveAndFlush(parcelModel);
        });
    }

    private List<ParcelStateRecord> getParcelStateRecords(CourierModel courierModel, List<ParcelModel> parcelModels, ParcelStateType parcelStateType) {
        return parcelModels
                .stream()
                .map(parcelModel -> prepareParcelStateRecord(parcelModel, courierModel, parcelStateType))
                .collect(Collectors.toList());
    }

    private List<ParcelStateRecord> getParcelStateRecords(MagazineModel magazineModel, List<ParcelModel> parcelModels) {
        return parcelModels
                .stream()
                .map(parcelModel -> prepareParcelStateRecord(parcelModel, magazineModel))
                .collect(Collectors.toList());
    }

    private List<ParcelModel> getParcelModels(List<Long> parcelIds) {
        return parcelIds
                .stream()
                .map(id -> parcelRepository.getById(id).orElseThrow(() -> new ParcelNotFound(id)))
                .collect(Collectors.toList());
    }

    private void verifyState(ParcelModel parcelModel, ParcelStateType... acceptableStates) {
        ParcelStateType currentState = ParcelData.of(parcelModel).getCurrentState().getState();
        if (Arrays.stream(acceptableStates).noneMatch(acceptableState -> acceptableState == currentState)) {
            throw new IllegalParcelState(parcelModel.getId(), currentState, acceptableStates);
        }
    }

}
