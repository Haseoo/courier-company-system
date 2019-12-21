package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.models.ClientModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class ClientData extends UserData {
    private String emailAddress;
    private String phoneNumber;
    private ClientType clientType;
    private List<ParcelData> sentParcelList;
    private List<ParcelData> observedParcelList;

    public static ClientData of(ClientModel model) {
        return ClientData
                .builder()
                .id(model.getId())
                .userName(model.getUserName())
                .password(model.getPassword())
                .active(model.getActive())
                .userType(UserUtils.getUserType(model))
                .clientType(model.getClientType())
                .emailAddress(model.getEmailAddress())
                .phoneNumber(model.getPhoneNumber())
                .sentParcelList(model.getSentParcels()
                        .stream()
                        .map(ParcelData::of)
                        .collect(Collectors.toList()))
                .observedParcelList(model.getObservedParcels()
                        .stream()
                        .map(ParcelData::of)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ClientData ofWithoutLists(ClientModel model) {
        return ClientData
                .builder()
                .id(model.getId())
                .userName(model.getUserName())
                .password(model.getPassword())
                .active(model.getActive())
                .userType(UserUtils.getUserType(model))
                .clientType(model.getClientType())
                .emailAddress(model.getEmailAddress())
                .phoneNumber(model.getPhoneNumber())
                .build();
    }
}
