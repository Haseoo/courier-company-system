package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.commandsdata.users.clients.ClientCompanyAddCommandData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ClientCompanyAddData {
    @NonNull
    private String userName;
    @NonNull
    private char[] password;
    @NonNull
    private Boolean active;
    @NonNull
    private String emailAddress;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String companyName;
    @NonNull
    private String nip;
    @NonNull
    private String representativeName;
    @NonNull
    private String representativeSurname;
    @NonNull
    private String representativeEmailAddress;
    @NonNull
    private String representativePhoneNumber;

    public static ClientCompanyAddData of(ClientCompanyAddCommandData commandData) {
        return ClientCompanyAddData
                .builder()
                .userName(commandData.getUserName())
                .password(commandData.getPassword())
                .active(true)
                .emailAddress(commandData.getEmailAddress())
                .phoneNumber(commandData.getPhoneNumber())
                .companyName(commandData.getCompanyName())
                .nip(commandData.getNip())
                .representativeName(commandData.getRepresentativeName())
                .representativeSurname(commandData.getRepresentativeSurname())
                .representativeEmailAddress(commandData.getRepresentativeEmailAddress())
                .representativePhoneNumber(commandData.getRepresentativePhoneNumber())
                .build();
    }
}
