package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.serviceexceptions.MagazineDoesNotExist;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalParcelState;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.PaidPickupFromMagazine;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotPaid;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.ports.*;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmailService;
import com.github.haseoo.courier.services.ports.MagazineService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.utilities.PinGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.EmployeeType.COURIER;
import static com.github.haseoo.courier.enums.ParcelStateType.*;
import static com.github.haseoo.courier.utilities.Utils.addWorkdays;
import static com.github.haseoo.courier.utilities.Constants.*;

@Service
@RequiredArgsConstructor
public class ParcelStateServiceImpl implements ParcelStateService {
    private final MagazineRepository magazineRepository;
    private final CourierRepository courierRepository;
    private final ParcelRepository parcelRepository;
    private final ParcelStateRepository parcelStateRepository;
    private final PinGenerator pinGenerator;
    private final EmailService emailService;
    private final MagazineService magazineService;
    private final CourierService courierService;
    private final EstimatedDeliveryTimeRepository estimatedDeliveryTimeRepository;

    @Override
    @Transactional
    public MagazineData addParcelsToMagazine(Long magazineId, List<Long> parcelIds) {
        MagazineModel magazineModel = magazineRepository.getById(magazineId).orElseThrow(() -> new MagazineDoesNotExist(magazineId));
        List<ParcelModel> parcelModels = getParcelModels(parcelIds);
        parcelModels.forEach(parcelModel -> changeParcelStateToMagazine(magazineModel, parcelModel));
        return magazineService.getById(magazineId);
    }

    @Override
    @Transactional
    public CourierData assignParcelsToCourier(Long courierId, List<Long> parcelIds) {
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        List<ParcelModel> parcelModels = getParcelModels(parcelIds);
        parcelModels.forEach(parcelModel -> changeParcelStateToAssign(courierModel, parcelModel));
        return courierService.getById(courierId);
    }

    @Override
    @Transactional
    public CourierData setAsPickedByCourier(Long courierId, Long parcelId, boolean wasPaid) {
        ParcelModel parcelModel = parcelRepository.getById(parcelId).orElseThrow(() -> new ParcelNotFound(parcelId));
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        if (pickingFromMagazine(parcelModel) && wasPaid) {
            throw new PaidPickupFromMagazine();
        }
        changeParcelStateToAtCourier(courierModel, parcelModel, wasPaid);
        return courierService.getById(courierId);
    }

    @Override
    @Transactional
    public ParcelData setParcelAsDelivered(Long courierId, Long parcelId, boolean wasPaid) {
        return changeOneParcelState(courierId, parcelId, DELIVERED, wasPaid);
    }

    @Override
    @Transactional
    public ParcelData setParcelReturned(Long courierId, Long parcelId, boolean wasPaid) {
        return changeOneParcelState(courierId, parcelId, RETURNED, wasPaid);
    }

    @Transactional
    @Override
    public void removeCurrentState(Long id) {
        ParcelModel parcel = parcelRepository.getById(id).orElseThrow(() -> new ParcelNotFound(id));
        Long lastStateId = parcel.getParcelStates().stream()
                .max(Comparator.comparing(ParcelStateRecord::getChangeDate))
                .orElseThrow(IllegalParcelState::new)
                .getId();
        parcelStateRepository.deleteById(lastStateId);
    }

    private boolean pickingFromMagazine(ParcelModel parcelModel) {
        return ParcelData.of(parcelModel).getCurrentState().getState().equals(ASSIGNED) && ParcelData.of(parcelModel).wasInMagazine();
    }

    private void changeParcelStateToMagazine(MagazineModel magazineModel, ParcelModel parcelModel) {
        verifyState(parcelModel, AT_COURIER);
        ParcelStateRecord record = new ParcelStateRecord();
        record.setState(IN_MAGAZINE);
        record.setParcel(parcelModel);
        record.setMagazine(magazineModel);
        record.setChangeDate(LocalDateTime.now());
        parcelModel.getParcelStates().add(record);
        parcelModel.setExpectedCourierArrivalDate(addWorkdays(LocalDate.now(), estimatedDeliveryTimeRepository.getById(Long.valueOf(IDS)).getExpectedCourierArrivalAfterAddToMagazine()));
        parcelRepository.saveAndFlush(parcelModel);
        sentNotificationToReceiver(parcelModel);
    }

    private void changeParcelStateToAssign(CourierModel courierModel, ParcelModel parcelModel) {
        ParcelData parcelData = ParcelData.of(parcelModel);
        if (parcelData.getDateMoved() && LocalDate.now().isBefore(parcelData.getExpectedCourierArrivalDate())) {
            throw new IllegalParcelState();
        }
        verifyState(parcelModel, AT_SENDER, IN_MAGAZINE);
        ParcelStateRecord record = new ParcelStateRecord();
        record.setParcel(parcelModel);
        record.setCourier(courierModel);
        record.setState(ASSIGNED);
        record.setChangeDate(LocalDateTime.now());
        parcelModel.getParcelStates().add(record);
        parcelRepository.saveAndFlush(parcelModel);
    }

    private void changeParcelStateToAtCourier(CourierModel courierModel, ParcelModel parcelModel, boolean wasPaid) {
        verifyState(parcelModel, ASSIGNED);
        ParcelStateRecord record = new ParcelStateRecord();
        record.setParcel(parcelModel);
        record.setCourier(courierModel);
        record.setState(AT_COURIER);
        record.setChangeDate(LocalDateTime.now());
        parcelModel.getParcelStates().add(record);
        updateParcelAfterPickUp(parcelModel, wasPaid);
        parcelRepository.saveAndFlush(parcelModel);
    }

    private ParcelData changeOneParcelState(Long courierId, Long parcelId, ParcelStateType newState, boolean wasPaid) {
        CourierModel courierModel = courierRepository.getById(courierId).orElseThrow(() -> new EmployeeNotFoundException(courierId, COURIER));
        ParcelModel parcelModel = parcelRepository.getById(parcelId).orElseThrow(() -> new ParcelNotFound(parcelId));
        if (!parcelModel.getPaid() && !wasPaid) {
            throw new ParcelNotPaid(parcelId);
        }
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

    private void updateParcelAfterPickUp(ParcelModel parcelModel, boolean wasPaid) {
        if (ParcelData.of(parcelModel).wasInMagazine())
            parcelModel.setExpectedCourierArrivalDate(LocalDate.now());
        else {
            parcelModel.setExpectedCourierArrivalDate(null);
            parcelModel.setDateMoved(false);
            parcelModel.setPin(pinGenerator.getParcelPin());
        }
        if (wasPaid) {
            parcelModel.setPaid(true);
        }
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

    private void sentNotificationToReceiver(ParcelModel parcelModel) {
        if (!ParcelData.of(parcelModel).wasInMagazine()) {
            emailService.sentNotificationToReceiver(ParcelData.of(parcelModel));
        }
    }
}
