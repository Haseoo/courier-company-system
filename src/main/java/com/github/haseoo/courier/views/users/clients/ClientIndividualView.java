package com.github.haseoo.courier.views.users.clients;

import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ClientIndividualView {
    private Long id;
    private Boolean active;
    private String emailAddress;
    private String phoneNumber;
    private String name;
    private String surname;
    private String pesel;

    public static ClientIndividualView of(ClientIndividualData clientIndividualData) {
        return ClientIndividualView
                .builder()
                .id(clientIndividualData.getId())
                .active(clientIndividualData.getActive())
                .emailAddress(clientIndividualData.getEmailAddress())
                .phoneNumber(clientIndividualData.getPhoneNumber())
                .name(clientIndividualData.getName())
                .surname(clientIndividualData.getSurname())
                .pesel(clientIndividualData.getPesel())
                .build();
    }
}
