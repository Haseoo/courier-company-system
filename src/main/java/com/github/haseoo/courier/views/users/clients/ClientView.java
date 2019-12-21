package com.github.haseoo.courier.views.users.clients;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.servicedata.users.clients.ClientData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Value
public class ClientView {
    private Long id;
    private Boolean active;
    private String emailAddress;
    private String phoneNumber;
    private ClientType clientType;

    public static ClientView of(ClientData clientData) {
        return ClientView
                .builder()
                .id(clientData.getId())
                .active(clientData.getActive())
                .emailAddress(clientData.getEmailAddress())
                .phoneNumber(clientData.getPhoneNumber())
                .clientType(clientData.getClientType())
                .build();
    }
}
