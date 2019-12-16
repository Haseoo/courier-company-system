package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualAddCommandData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ClientIndividualAddData {
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
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String pesel;

    public static ClientIndividualAddData of(ClientIndividualAddCommandData commandData) {
        return ClientIndividualAddData
                .builder()
                .userName(commandData.getUserName())
                .password(commandData.getPassword())
                .active(true)
                .emailAddress(commandData.getEmailAddress())
                .phoneNumber(commandData.getPhoneNumber())
                .name(commandData.getName())
                .surname(commandData.getSurname())
                .pesel(commandData.getPesel())
                .build();
    }
}
