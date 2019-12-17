package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
public class ClientIndividualData extends ClientData {
    private String name;
    private String surname;
    private String pesel;

    public static ClientIndividualData of(ClientIndividualModel model) {
        return ClientIndividualData
                .builder()
                    .id(model.getId())
                    .userName(model.getUserName())
                    .password(model.getPassword())
                    .active(model.getActive())
                    .userType(UserUtils.getUserType(model))
                    .emailAddress(model.getEmailAddress())
                    .phoneNumber(model.getPhoneNumber())
                    .name(model.getName())
                    .surname(model.getSurname())
                    .pesel(model.getPesel())
                .build();
    }

}
