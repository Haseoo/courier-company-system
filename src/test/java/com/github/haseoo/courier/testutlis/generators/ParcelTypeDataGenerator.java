package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.ParcelTypeModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.constants.ParcelTypeConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ParcelTypeDataGenerator {
    public static ParcelTypeModel getActiveParcelTypeModel() {
        ParcelTypeModel parcelTypeModel = new ParcelTypeModel();
        parcelTypeModel.setName(TEST_PARCEL_TYPE_NAME);
        parcelTypeModel.setDescription(TEST_PARCEL_TYPE_DESCRIPTION);
        parcelTypeModel.setPrice(TEST_PARCEL_TYPE_PRICE);
        parcelTypeModel.setActive(TEST_PARCEL_TYPE_ACTIVE);
        return parcelTypeModel;
    }

    public static ParcelTypeModel getNotActiveParcelTypeModel() {
        ParcelTypeModel parcelTypeModel = new ParcelTypeModel();
        parcelTypeModel.setName(TEST_PARCEL_TYPE_NAME);
        parcelTypeModel.setDescription(TEST_PARCEL_TYPE_DESCRIPTION);
        parcelTypeModel.setPrice(TEST_PARCEL_TYPE_PRICE);
        parcelTypeModel.setActive(TEST_PARCEL_TYPE_NOT_ACTIVE);
        return parcelTypeModel;
    }
}
