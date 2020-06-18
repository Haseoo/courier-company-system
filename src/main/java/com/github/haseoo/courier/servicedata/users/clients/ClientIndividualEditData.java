package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualEditCommandData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Value
@Builder(access = PUBLIC)
@AllArgsConstructor(access = PRIVATE)
public class ClientIndividualEditData {
    private char[] password;
    private String emailAddress;
    private String phoneNumber;
    private String name;
    private String surname;

    public static ClientIndividualEditData of(ClientIndividualEditCommandData commandData) {
        return ClientIndividualEditData
                .builder()
                .password(commandData.getPassword())
                .emailAddress(commandData.getEmailAddress())
                .phoneNumber(commandData.getPhoneNumber())
                .name(commandData.getName())
                .surname(commandData.getSurname())
                .build();
    }
}
