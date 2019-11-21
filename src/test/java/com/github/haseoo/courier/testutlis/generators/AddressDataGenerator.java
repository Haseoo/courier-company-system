package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.AddressModel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.github.haseoo.courier.testutlis.Constants.AddressDataConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AddressDataGenerator {
    public static AddressModel getAddressModel() {
        /*AddressModel addressModel = AddressModel();
        addressModel.setAddressId(null);
        addressModel.setApartmentNumber(TEST_APARTMENT_NUMBER);
        addressModel.setCity(TEST_CITY);
        addressModel.setHouseNumber(TEST_HOUSE_NUMBER);
        addressModel.setPostalCode(TEST_POSTAL_CODE);
        addressModel.setReceivePackages(new ArrayList<>());
        addressModel.setSendPackages(new ArrayList<>());*/
        return AddressModel.builder().apartmentNumber(TEST_APARTMENT_NUMBER).city(TEST_CITY).postalCode(TEST_POSTAL_CODE).houseNumber(TEST_HOUSE_NUMBER).build();
    }
}
