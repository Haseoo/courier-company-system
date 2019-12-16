package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.models.ClientCompanyModel;
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
public class ClientCompanyData extends ClientData {
    private String companyName;
    private String nip;
    private String representativeName;
    private String representativeSurname;
    private String representativeEmailAddress;
    private String representativePhoneNumber;

    public static ClientCompanyData of(ClientCompanyModel model) {
        return ClientCompanyData
                .builder()
                .id(model.getId())
                .userName(model.getUserName())
                .password(model.getPassword())
                .active(model.getActive())
                .userType(UserUtils.getUserType(model))
                .phoneNumber(model.getPhoneNumber())
                .emailAddress(model.getEmailAddress())
                .companyName(model.getCompanyName())
                .nip(model.getNip())
                .representativeName(model.getRepresentativeName())
                .representativeSurname(model.getRepresentativeSurname())
                .representativeEmailAddress(model.getRepresentativeEmailAddress())
                .representativePhoneNumber(model.getRepresentativePhoneNumber())
                .build();
    }

}
