package com.github.haseoo.courier.views.users.clients;

import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ClientCompanyView {
    private Long id;
    private Boolean active;
    private String emailAddress;
    private String phoneNumber;
    private String companyName;
    private String nip;
    private String representativeName;
    private String representativeSurname;
    private String representativeEmailAddress;
    private String representativePhoneNumber;

    public static ClientCompanyView of(ClientCompanyData clientCompanyData) {
        return ClientCompanyView
                .builder()
                .id(clientCompanyData.getId())
                .active(clientCompanyData.getActive())
                .emailAddress(clientCompanyData.getEmailAddress())
                .phoneNumber(clientCompanyData.getPhoneNumber())
                .companyName(clientCompanyData.getCompanyName())
                .nip(clientCompanyData.getNip())
                .representativeName(clientCompanyData.getRepresentativeName())
                .representativeSurname(clientCompanyData.getRepresentativeSurname())
                .representativeEmailAddress(clientCompanyData.getRepresentativeEmailAddress())
                .representativePhoneNumber(clientCompanyData.getRepresentativePhoneNumber())
                .build();
    }
}
