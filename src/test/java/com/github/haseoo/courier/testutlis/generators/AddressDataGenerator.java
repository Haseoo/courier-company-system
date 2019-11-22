package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.querydata.AddressQueryData;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.constants.AddressConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AddressDataGenerator {
    public static AddressModel getAddressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setFlatNumber(TEST_FLAT_NUMBER);
        addressModel.setCity(TEST_CITY);
        addressModel.setBuildingNumber(TEST_BUILDING_NUMBER);
        addressModel.setPostalCode(TEST_POSTAL_CODE);
        addressModel.setStreet(TEST_STREET);
        return addressModel;
    }

    public static AddressQueryData getExistentQueryData() {
        return AddressQueryData
                .builder()
                .city(TEST_CITY)
                .postalCode(TEST_POSTAL_CODE)
                .buildingNumber(TEST_BUILDING_NUMBER)
                .street(TEST_STREET)
                .flatNumber(TEST_FLAT_NUMBER)
                .build();
    }
    public static AddressQueryData getNotExistentQueryData() {
        return AddressQueryData
                .builder()
                .city(TEST_CITY)
                .postalCode(TEST_POSTAL_CODE)
                .buildingNumber(TEST_BUILDING_NUMBER)
                .street(NEW_STREET)
                .flatNumber(TEST_FLAT_NUMBER)
                .build();
    }
}
