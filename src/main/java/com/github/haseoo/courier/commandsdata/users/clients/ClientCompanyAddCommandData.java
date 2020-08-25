package com.github.haseoo.courier.commandsdata.users.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserAddCommandData;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Validated
@Value
public class ClientCompanyAddCommandData extends UserAddCommandData {
    private String emailAddress;
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Company name cannot be empty")
    private String companyName;
    @NotBlank(message = "Nip cannot be empty")
    private String nip;
    @NotBlank(message = "Representative name cannot be empty")
    private String representativeName;
    @NotBlank(message = "Representative surname cannot be empty")
    private String representativeSurname;
    private String representativeEmailAddress;
    @NotBlank(message = "Representative phone number cannot be empty")
    private String representativePhoneNumber;

    @JsonCreator
    public ClientCompanyAddCommandData(@JsonProperty(value = "userName", required = true) String userName,
                                       @JsonProperty(value = "password", required = true) char[] password,
                                       @JsonProperty(value = "emailAddress", required = true) String emailAddress,
                                       @JsonProperty(value = "phoneNumber", required = true) String phoneNumber,
                                       @JsonProperty(value = "companyName", required = true) String companyName,
                                       @JsonProperty(value = "nip", required = true) String nip,
                                       @JsonProperty(value = "representativeName", required = true) String representativeName,
                                       @JsonProperty(value = "representativeSurname", required = true) String representativeSurname,
                                       @JsonProperty(value = "representativeEmailAddress", required = true) String representativeEmailAddress,
                                       @JsonProperty(value = "representativePhoneNumber", required = true) String representativePhoneNumber) {
        super(userName, password);
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.nip = nip;
        this.representativeName = representativeName;
        this.representativeSurname = representativeSurname;
        this.representativeEmailAddress = representativeEmailAddress;
        this.representativePhoneNumber = representativePhoneNumber;
    }
}
