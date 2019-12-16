package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.models.ClientModel;
import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class ClientData extends UserData {
    private String emailAddress;
    private String phoneNumber;

    public static ClientData of(ClientModel model) {
        return ClientData
                .builder()
                .id(model.getId())
                .userName(model.getUserName())
                .password(model.getPassword())
                .active(model.getActive())
                .userType(UserUtils.getUserType(model))
                .emailAddress(model.getEmailAddress())
                .phoneNumber(model.getPhoneNumber())
                .build();
    }
}
