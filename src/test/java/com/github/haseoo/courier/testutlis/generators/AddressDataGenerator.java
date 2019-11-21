package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.AddressModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.Constants.AddressConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AddressDataGenerator {
    public static AddressModel getAddressModel() {
        AddressModel addressModel = new AddressModel();
        addressModel.setFlatNumber(TEST_APARTMENT_NUMBER);
        addressModel.setCity(TEST_CITY);
        addressModel.setBuildingNumber(TEST_HOUSE_NUMBER);
        addressModel.setPostalCode(TEST_POSTAL_CODE);
        addressModel.setStreet(TEST_STREET);
        return addressModel;
    }
}
