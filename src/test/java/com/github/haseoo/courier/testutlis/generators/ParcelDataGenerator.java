package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.github.haseoo.courier.enums.ParcelStateType.AT_COURIER_FROM_SENDER;
import static com.github.haseoo.courier.testutlis.constants.ParcelConstants.TEST_PIN;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ParcelDataGenerator {
    public static ParcelModel getParcelModel(ParcelTypeModel type, ClientCompanyModel client) {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setPin(TEST_PIN);
        parcelModel.setParcelType(type);
        parcelModel.setDeliveryAddress(getAddressModel());
        parcelModel.setSenderAddress(getAddressModel());
        parcelModel.setSender(client);
        parcelModel.setReceiverContactData(getReceiverInfoModel());
        parcelModel.setExpectedDeliveryTime(LocalDate.now());
        parcelModel.setPriority(false);
        parcelModel.setParcelFee(type.getPrice());
        parcelModel.setPaid(false);
        parcelModel.setDateMoved(false);
        parcelModel.setParcelStates(new ArrayList<>());
        return parcelModel;
    }

    public static ParcelStateRecord getTestRecordModel(ParcelModel parcel, CourierModel courier) {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setParcel(parcel);
        parcelStateRecord.setState(AT_COURIER_FROM_SENDER);
        parcelStateRecord.setCourier(courier);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        return parcelStateRecord;
    }
}
