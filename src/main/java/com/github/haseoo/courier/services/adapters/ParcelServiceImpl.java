package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.*;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.ports.*;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import com.github.haseoo.courier.services.ports.*;
import com.github.haseoo.courier.utilities.PinGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.ParcelStateType.*;
import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;
import static com.github.haseoo.courier.utilities.Utils.addWorkdays;
import static com.github.haseoo.courier.utilities.Utils.isParcelMoveable;
import static java.lang.Math.abs;
import static java.time.temporal.ChronoUnit.DAYS;
import static com.github.haseoo.courier.utilities.Constants.*;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final AddressService addressService;
    private final ReceiverInfoService receiverInfoService;
    private final ParcelTypeRepository parcelTypeRepository;
    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final ClientIndividualRepository clientIndividualRepository;
    private final PinGenerator pinGenerator;
    private final UserDetailsServiceImpl userDetailsService;
    private final EmailService emailService;
    private final MagazineService magazineService;
    private final PostalCodeHelper postalCodeHelper;
    private final EstimatedDeliveryTimeRepository estimatedDeliveryTimeRepository;

    @Override
    @Transactional
    public ParcelData add(ParcelAddData parcelAddData) {
        userDetailsService.verifyEditResource(parcelAddData.getSenderId());
        validatePostalCode(parcelAddData.getSenderAddress(), parcelAddData.getDeliveryAddress());
        ParcelModel parcelModel = parcelRepository.saveAndFlush(prepareParcelModel(parcelAddData));
        parcelModel.setParcelStates(new ArrayList<>());
        parcelModel.getParcelStates().add(getFistState(parcelModel));
        ParcelData parcelData = ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
        emailService.sentNotificationToSender(parcelData);
        return parcelData;
    }

    @Override
    @Transactional
    public ParcelData edit(Long id, ParcelEditData parcelEditData) {
        validatePostalCode(parcelEditData.getSenderAddress(), parcelEditData.getDeliveryAddress());
        ParcelModel parcelModel = prepareEditedParcelModel(id, parcelEditData);
        return ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
    }

    private void validatePostalCode(AddressOperationData senderAddress, AddressOperationData deliveryAddress) {
        postalCodeHelper.validatePostalCode(senderAddress.getCity(),
                senderAddress.getPostalCode());
        postalCodeHelper.validatePostalCode(deliveryAddress.getCity(),
                deliveryAddress.getPostalCode());
    }

    @Override
    @Transactional
    public ParcelData setParcelToReturn(Long id) {
        ParcelModel parcelModel = parcelRepository
                .getById(id)
                .orElseThrow(() -> new ParcelTypeNotFound(id));
        if (ParcelData.of(parcelModel).getCurrentState().getState() != IN_MAGAZINE) {
            throw new IllegalParcelState();
        }
        setReceiverAsSender(parcelModel);
        parcelModel.setPin(pinGenerator.getParcelPin());
        addressService.consume(AddressOperationData.of(parcelModel.getSenderAddress()), parcelModel::setDeliveryAddress);
        ParcelData parcelData = ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
        emailService.sentReturnNotification(parcelData);
        return parcelData;
    }

    @Override
    public List<ParcelData> getList() {
        return parcelRepository.getList()
                .stream()
                .map(ParcelData::of)
                .collect(Collectors.toList());
    }

    @Override
    public ParcelData getById(Long id) {
        return ParcelData.of(parcelRepository.getById(id).orElseThrow(() -> new ParcelNotFound(id)));
    }

    @Override
    public void delete(Long id) {
        ParcelData parcelData = ParcelData.of(parcelRepository.getById(id).orElseThrow(() -> new ParcelNotFound(id)));
        userDetailsService.verifyEditResource(parcelData.getSender().getId());
        if (parcelData.getCurrentState().getState() != AT_SENDER) {
            throw new IllegalParcelState();
        }
        parcelRepository.delete(id);
    }

    @Override
    public ParcelData moveDate(Long id, char[] pin, LocalDate newDate) {
        ParcelModel parcelModel = parcelRepository
                .getById(id)
                .orElseThrow(() -> new ParcelTypeNotFound(id));
        validatePin(pin, parcelModel);
        validateMoveable(parcelModel);
        validateNewDate(newDate, parcelModel);
        parcelModel.setExpectedCourierArrivalDate(newDate);
        parcelModel.setDateMoved(true);
        return ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
    }

    private void validateNewDate(LocalDate newDate, ParcelModel parcelModel) {
        if (newDate.isBefore(LocalDate.now()) || !newDate.isAfter(parcelModel.getExpectedCourierArrivalDate())) {
            throw new IllegalMoveDate();
        }
        if (abs(DAYS.between(newDate, parcelModel.getExpectedCourierArrivalDate())) > estimatedDeliveryTimeRepository.getById(Long.valueOf(IDS)).getMaxMoveDayAfter()) {
            throw new IllegalMoveDate();
        }
        if (newDate.getDayOfWeek() == DayOfWeek.SATURDAY || newDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalMoveDate();
        }
    }

    private void validateMoveable(ParcelModel parcelModel) {
        if (!isParcelMoveable(ParcelData.of(parcelModel))) {
            throw new IllegalParcelState();
        }
    }

    private void validatePin(char[] pin, ParcelModel parcelModel) {
        if (!Arrays.equals(pin, parcelModel.getPin())) {
            throw new IncorrectParcelException();
        }
    }

    private ParcelModel prepareEditedParcelModel(Long id, ParcelEditData parcelEditData) {
        ParcelModel parcelModel = parcelRepository.getById(id).orElseThrow(() -> new ParcelNotFound(id));
        checkParcel(parcelModel);
        if (parcelEditData.getReceiverContactData() != null) {
            receiverInfoService.consume(parcelEditData.getReceiverContactData(), parcelModel::setReceiverContactData);
        }
        if (parcelEditData.getSenderAddress() != null) {
            addressService.consume(parcelEditData.getSenderAddress(), parcelModel::setSenderAddress);
        }
        if (parcelEditData.getDeliveryAddress() != null) {
            addressService.consume(parcelEditData.getDeliveryAddress(), parcelModel::setDeliveryAddress);
        }
        if (parcelEditData.getPriority() != null) {
            parcelModel.setPriority(parcelEditData.getPriority());
        }
        if (parcelEditData.getParcelFee() != null) {
            parcelModel.setParcelFee(parcelEditData.getParcelFee());
        }
        return parcelModel;
    }

    private ParcelModel prepareParcelModel(ParcelAddData parcelAddData) {
        ParcelTypeModel parcelTypeModel = parcelTypeRepository
                .getById(parcelAddData.getParcelTypeId())
                .orElseThrow(() -> new ParcelTypeNotFound(parcelAddData.getParcelTypeId()));
        ClientModel clientModel = clientRepository.getById(parcelAddData.getSenderId())
                .orElseThrow(() -> new ClientNotFound(parcelAddData.getSenderId()));
        if (!parcelTypeModel.getActive()) {
            throw new ParcelInactiveType();
        }
        return buildParcelModel(parcelAddData, parcelTypeModel, clientModel);
    }

    private ParcelModel buildParcelModel(ParcelAddData parcelAddData, ParcelTypeModel parcelTypeModel, ClientModel clientModel) {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setPin(pinGenerator.getParcelPin());
        parcelModel.setParcelType(parcelTypeModel);
        addressService.consume(parcelAddData.getDeliveryAddress(), parcelModel::setDeliveryAddress);
        addressService.consume(parcelAddData.getSenderAddress(), parcelModel::setSenderAddress);
        parcelModel.setSender(clientModel);
        receiverInfoService.consume(parcelAddData.getReceiverContactData(), parcelModel::setReceiverContactData);
        parcelModel.setPriority(parcelAddData.getPriority());
        parcelModel.setParcelFee(parcelAddData.getParcelFee());
        parcelModel.setPaid(false);
        parcelModel.setDateMoved(false);
        parcelModel.setExpectedCourierArrivalDate(addWorkdays(LocalDate.now(), estimatedDeliveryTimeRepository.getById(Long.valueOf(IDS)).getExpectedCourierArrivalAfterAddToMagazine()));
        return parcelModel;
    }

    private ParcelStateRecord getFistState(ParcelModel parcelModel) {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setParcel(parcelModel);
        parcelStateRecord.setState(ParcelStateType.AT_SENDER);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        magazineService.consumeClosestMagazine(AddressData.of(parcelModel.getDeliveryAddress()), parcelStateRecord::setMagazine);
        return parcelStateRecord;
    }

    private void checkParcel(ParcelModel parcelModel) {
        userDetailsService.verifyEditResource(parcelModel.getSender().getId());
        ParcelStateType currentState = ParcelData.of(parcelModel).getCurrentState().getState();
        if (!(currentState == AT_SENDER || currentState == RETURNED)) {
            throw new IllegalParcelState();
        }
    }

    private void setReceiverAsSender(ParcelModel parcelModel) {
        parcelModel.setToReturn(true);
        ClientModel client = clientRepository
                .getById(parcelModel.getSender().getId())
                .orElseThrow(() -> new ClientNotFound(parcelModel.getSender().getId()));
        if (client.getClientType() == ClientType.COMPANY) {
            ClientCompanyModel clientCompany = clientCompanyRepository
                    .getById(client.getId())
                    .orElseThrow(() -> new ClientNotFound(client.getId()));
            receiverInfoService.consume(ReceiverInfoOperationData.of(clientCompany), parcelModel::setReceiverContactData);
        } else if (parcelModel.getSender().getClientType() == ClientType.INDIVIDUAL) {
            ClientIndividualModel clientIndividual = clientIndividualRepository
                    .getById(client.getId())
                    .orElseThrow(() -> new ClientNotFound(client.getId()));
            receiverInfoService.consume(ReceiverInfoOperationData.of(clientIndividual), parcelModel::setReceiverContactData);
        } else {
            throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }
}
