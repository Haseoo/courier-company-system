package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.querydata.ReceiverInfoQueryData;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.constants.ReceiverInfoConstants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReceiverInfoDataGenerator {

    public static ReceiverInfoModel getReceiverInfoModel() {
        ReceiverInfoModel receiverInfoModel = new ReceiverInfoModel();
        receiverInfoModel.setName(TEST_RECEIVERINFO_TYPE_NAME);
        receiverInfoModel.setSurname(TEST_RECEIVERINFO_TYPE_SURNAME);
        receiverInfoModel.setEmailAddress(TEST_RECEIVERINFO_TYPE_EMAILADDRESS);
        receiverInfoModel.setPhoneNumber(TEST_RECEIVERINFO_TYPE_PHONENUMBER);
        return receiverInfoModel;
    }

    public static ReceiverInfoQueryData getExistentQueryData() {
        return ReceiverInfoQueryData
                .builder()
                .name(TEST_RECEIVERINFO_TYPE_NAME)
                .surname(TEST_RECEIVERINFO_TYPE_SURNAME)
                .emailAddress(TEST_RECEIVERINFO_TYPE_EMAILADDRESS)
                .phoneNumber(TEST_RECEIVERINFO_TYPE_PHONENUMBER)
                .build();
    }

    public static ReceiverInfoQueryData getNotExistentQueryData() {
        return ReceiverInfoQueryData
                .builder()
                .name(TEST_RECEIVERINFO_TYPE_NAME)
                .surname(TEST_RECEIVERINFO_TYPE_SURNAME)
                .emailAddress(TEST_RECEIVERINFO_TYPE_EMAILADDRESS)
                .phoneNumber(NEW_TEST_RECEIVERINFO_TYPE_PHONENUMBER)
                .build();
    }
}
