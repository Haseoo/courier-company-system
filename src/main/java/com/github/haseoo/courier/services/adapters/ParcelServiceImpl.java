package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalParcelState;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelInactiveType;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.models.ClientModel;
import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.repositories.ports.ClientRepository;
import com.github.haseoo.courier.repositories.ports.ParcelRepository;
import com.github.haseoo.courier.repositories.ports.ParcelTypeRepository;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.services.ports.AddressService;
import com.github.haseoo.courier.services.ports.ParcelService;
import com.github.haseoo.courier.services.ports.ReceiverInfoService;
import com.github.haseoo.courier.utilities.PinGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.ParcelStateType.AT_SENDER;
import static com.github.haseoo.courier.enums.ParcelStateType.RETURNED;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final AddressService addressService;
    private final ReceiverInfoService receiverInfoService;
    private final ParcelTypeRepository parcelTypeRepository;
    private final ClientRepository clientRepository;
    private final PinGenerator pinGenerator;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    @Transactional
    public ParcelData add(ParcelAddData parcelAddData) {
        userDetailsService.verifyEditResource(parcelAddData.getSenderId());
        ParcelModel parcelModel = parcelRepository.saveAndFlush(prepareParcelModel(parcelAddData));
        parcelModel.setParcelStates(new ArrayList<>());
        parcelModel.getParcelStates().add(getFistState(parcelModel));
        return ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
    }

    @Override
    @Transactional
    public ParcelData edit(Long id, ParcelEditData parcelEditData) {
        ParcelModel parcelModel = prepareEditedParcelModel(id, parcelEditData);
        return ParcelData.of(parcelRepository.saveAndFlush(parcelModel));
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

    @Override
    public List<ParcelData> getList() {
        return parcelRepository.getList()
                .stream()
                .map(ParcelData::of)
                .collect(Collectors.toList());
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
        return parcelModel;
    }

    private ParcelStateRecord getFistState(ParcelModel parcelModel) {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setParcel(parcelModel);
        parcelStateRecord.setState(ParcelStateType.AT_SENDER);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        return parcelStateRecord;
    }

    private void checkParcel(ParcelModel parcelModel) {
        userDetailsService.verifyEditResource(parcelModel.getSender().getId());
        ParcelStateType currentState = ParcelData.of(parcelModel).getCurrentState().getState();
        if (!(currentState == AT_SENDER || currentState == RETURNED)) {
            throw new IllegalParcelState();
        }
    }
}
