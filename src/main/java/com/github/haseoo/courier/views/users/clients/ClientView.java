package com.github.haseoo.courier.views.users.clients;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.servicedata.users.clients.ClientData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Value
public class ClientView {
    private Long id;
    private UserType userType;
    private Boolean active;
    private String emailAddress;
    private String phoneNumber;

    public static ClientView of(ClientData clientData) {
        return ClientView
                .builder()
                .id(clientData.getId())
                .userType(clientData.getUserType())
                .active(clientData.getActive())
                .emailAddress(clientData.getEmailAddress())
                .phoneNumber(clientData.getPhoneNumber())
                .build();
    }
}
