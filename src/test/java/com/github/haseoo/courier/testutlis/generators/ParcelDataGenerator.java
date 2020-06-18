package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.models.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.github.haseoo.courier.testutlis.constants.ParcelConstants.TEST_PIN;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getMagazineModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ParcelDataGenerator {
    public static ParcelModel getParcelModel(ParcelTypeModel type,
                                             ClientCompanyModel client,
                                             AddressModel addressModel,
                                             ReceiverInfoModel receiverInfoModel) {
        ParcelModel parcelModel = new ParcelModel();
        parcelModel.setPin(TEST_PIN);
        parcelModel.setParcelType(type);
        parcelModel.setDeliveryAddress(addressModel);
        parcelModel.setSenderAddress(addressModel);
        parcelModel.setSender(client);
        parcelModel.setReceiverContactData(receiverInfoModel);
        parcelModel.setExpectedCourierArrivalDate(LocalDate.now());
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
        parcelStateRecord.setState(ParcelStateType.AT_COURIER);
        parcelStateRecord.setCourier(courier);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        return parcelStateRecord;
    }

    public static ParcelStateRecord getTestMagazineRecordModel(ParcelModel parcel, MagazineModel magazine) {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setParcel(parcel);
        parcelStateRecord.setState(ParcelStateType.AT_SENDER);
        parcelStateRecord.setMagazine(magazine);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        return parcelStateRecord;
    }

    public static ParcelStateRecord getTestMagazineRecordModelIn(ParcelModel parcel, MagazineModel magazine) {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setParcel(parcel);
        parcelStateRecord.setState(ParcelStateType.IN_MAGAZINE);
        parcelStateRecord.setMagazine(magazine);
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        return parcelStateRecord;
    }

    public static ParcelModel getParcelAtSender() {
        ParcelModel parcelModel = getParcelModel(getActiveParcelTypeModel(),
                getCompanyClientModel(),
                AddressDataGenerator.getAddressModel(),
                getReceiverInfoModel());
        parcelModel.setId(1L);
        parcelModel.setSenderAddress(AddressDataGenerator.getAddressModel());
        parcelModel.setObservingClients(new ArrayList<>());
        parcelModel.setParcelStates(new ArrayList<>());
        MagazineModel magazineModel = getMagazineModel();
        magazineModel.setId(1L);
        ParcelStateRecord parcelStateRecord = getTestMagazineRecordModel(parcelModel, magazineModel);
        magazineModel.getParcelStates().add(parcelStateRecord);
        parcelModel.getParcelStates().add(parcelStateRecord);
        return parcelModel;
    }

    public static ParcelModel getInMagazine() {
        ParcelModel parcelModel = getParcelAtSender();
        MagazineModel magazineModel = getMagazineModel();
        magazineModel.setId(1L);
        ParcelStateRecord parcelStateRecord = getTestMagazineRecordModelIn(parcelModel, magazineModel);
        parcelStateRecord.setChangeDate(LocalDateTime.now().plusDays(1));
        magazineModel.getParcelStates().add(parcelStateRecord);
        return parcelModel;
    }
}
