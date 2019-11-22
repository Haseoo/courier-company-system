package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.CourierModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.constants.UsersConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UsersDataGenerator {
    public static CourierModel getCourierModel() {
        CourierModel courierModel = new CourierModel();
        courierModel.setPassword(TEST_USER_PASSWD.toCharArray());
        courierModel.setUserName(TEST_USER_NAME);
        courierModel.setPesel(TEST_PESEL);
        courierModel.setUserName(TEST_NAME);
        courierModel.setSurname(TEST_SURNAME);
        courierModel.setPhoneNumber(TEST_PHONE_NUMBER);
        courierModel.setName(TEST_NAME);
        return courierModel;
    }
}
