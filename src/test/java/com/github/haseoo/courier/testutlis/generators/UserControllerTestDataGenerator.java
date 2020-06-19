package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyData;
import com.github.haseoo.courier.servicedata.users.clients.ClientData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserControllerTestDataGenerator {
    public static  ClientData getClientData() {
        return ClientData.builder()
                .clientType(ClientType.COMPANY)
                .emailAddress("test@tets.ts")
                .observedParcelList(new ArrayList<>())
                .sentParcelList(new ArrayList<>())
                .phoneNumber("12345")
                .build();
    }

    public static ClientCompanyData getClientCompanyData() {
        return ClientCompanyData.builder()
                .id(1L)
                .userName("Test")
                .password("".toCharArray())
                .active(true)
                .userType(UserType.COMPANY_CLIENT)
                .phoneNumber("1234")
                .emailAddress("test")
                .companyName("testName")
                .nip("1234")
                .representativeName("RepTest")
                .representativeSurname("SurTets")
                .representativeEmailAddress("test@t.com")
                .representativePhoneNumber("12345")
                .build();
    }

    public static ClientIndividualData getClientIndividualData() {
        return ClientIndividualData.builder()
                .id(1L)
                .userName("Test")
                .password("".toCharArray())
                .active(true)
                .userType(UserType.COMPANY_CLIENT)
                .phoneNumber("1234")
                .emailAddress("test")
                .pesel("12345")
                .build();
    }
}
