package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.CourierModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.constants.UsersConstants.TEST_USER_NAME;
import static com.github.haseoo.courier.testutlis.constants.UsersConstants.TEST_USER_PASSWD;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UsersDataGenerator {
    public static CourierModel getCourierModel() {
        CourierModel courierModel = new CourierModel();
        courierModel.setPassword(TEST_USER_PASSWD.toCharArray());
        courierModel.setUserName(TEST_USER_NAME);
        return courierModel;
    }
}
