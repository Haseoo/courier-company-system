package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        courierModel.setActive(true);
        courierModel.setParcelStates(new ArrayList<>());
        return courierModel;
    }

    public static LogisticianModel getLogisticianModel() {
        LogisticianModel logisticianModel = new LogisticianModel();
        logisticianModel.setPassword(TEST_USER_PASSWD.toCharArray());
        logisticianModel.setUserName(TEST_USER_NAME);
        logisticianModel.setPesel(TEST_PESEL);
        logisticianModel.setUserName(TEST_NAME);
        logisticianModel.setSurname(TEST_SURNAME);
        logisticianModel.setPhoneNumber(TEST_PHONE_NUMBER);
        logisticianModel.setName(TEST_NAME);
        logisticianModel.setActive(true);
        return logisticianModel;
    }

    public static ClientIndividualModel getIndividualClientModel() {
        ClientIndividualModel clientIndividualModel = new ClientIndividualModel();
        clientIndividualModel.setName(TEST_NAME);
        clientIndividualModel.setPesel(TEST_PESEL);
        clientIndividualModel.setSurname(TEST_SURNAME);
        clientIndividualModel.setEmailAddress(TEST_EMAIL);
        clientIndividualModel.setPhoneNumber(TEST_PHONE_NUMBER);
        clientIndividualModel.setUserName(TEST_USER_NAME);
        clientIndividualModel.setPassword(TEST_USER_PASSWD.toCharArray());
        clientIndividualModel.setClientType(ClientType.INDIVIDUAL);
        clientIndividualModel.setActive(true);
        return clientIndividualModel;
    }

    public static ClientCompanyModel getCompanyClientModel() {
        ClientCompanyModel clientCompanyModel = new ClientCompanyModel();
        clientCompanyModel.setRepresentativeName(TEST_NAME);
        clientCompanyModel.setNip(TEST_NIP);
        clientCompanyModel.setRepresentativeSurname(TEST_SURNAME);
        clientCompanyModel.setEmailAddress(TEST_EMAIL);
        clientCompanyModel.setPhoneNumber(TEST_PHONE_NUMBER);
        clientCompanyModel.setRepresentativeEmailAddress(TEST_EMAIL);
        clientCompanyModel.setRepresentativePhoneNumber(TEST_PHONE_NUMBER);
        clientCompanyModel.setUserName(TEST_USER_NAME);
        clientCompanyModel.setPassword(TEST_USER_PASSWD.toCharArray());
        clientCompanyModel.setCompanyName(TEST_COMPANY_NAME);
        clientCompanyModel.setClientType(ClientType.COMPANY);
        clientCompanyModel.setActive(true);
        return clientCompanyModel;
    }

    public static List<EmployeeModel> getEmployeeModelList() {
        return Arrays.asList(getCourierModel(), getLogisticianModel());
    }

    public static List<CourierModel> getCourierModelList() {
        return Arrays.asList(getCourierModel(), getCourierModel());
    }

    public static EmployeeAddOperationData getCourierOperationData() {
        return EmployeeAddOperationData.builder()
                .active(true)
                .name(TEST_NAME)
                .userName(TEST_USER_NAME)
                .password(TEST_USER_PASSWD.toCharArray())
                .pesel(TEST_PESEL)
                .phoneNumber(TEST_PHONE_NUMBER)
                .surname(TEST_SURNAME)
                .build();
    }
}
