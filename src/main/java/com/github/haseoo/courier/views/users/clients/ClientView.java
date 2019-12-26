package com.github.haseoo.courier.views.users.clients;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.servicedata.users.clients.ClientData;
import com.github.haseoo.courier.views.parcels.ParcelView;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Value
public class ClientView {
    private Long id;
    private Boolean active;
    private String emailAddress;
    private String phoneNumber;
    private ClientType clientType;
    private List<ParcelView.ParcelClientView> sendParcels;

    public static ClientView of(ClientData clientData) {
        return ClientView
                .builder()
                .id(clientData.getId())
                .active(clientData.getActive())
                .emailAddress(clientData.getEmailAddress())
                .phoneNumber(clientData.getPhoneNumber())
                .clientType(clientData.getClientType())
                .sendParcels(clientData.getSentParcelList()
                        .stream()
                        .map(ParcelView.ParcelClientView::of)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ClientView ofWithoutParcels(ClientData clientData) {
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
