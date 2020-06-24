package com.github.haseoo.courier.commandsdata.users.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserEditCommandData;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class ClientCompanyEditCommandData extends UserEditCommandData {
    private String emailAddress;
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Company name cannot be empty")
    private String companyName;
    @NotBlank(message = "Representative name cannot be empty")
    private String representativeName;
    @NotBlank(message = "Representative surname cannot be empty")
    private String representativeSurname;
    private String representativeEmailAddress;
    @NotBlank(message = "Representative phone number cannot be empty")
    private String representativePhoneNumber;

    @JsonCreator
    public ClientCompanyEditCommandData(@JsonProperty(value = "password") char[] password,
                                        @JsonProperty(value = "emailAddress") String emailAddress,
                                        @JsonProperty(value = "phoneNumber") String phoneNumber,
                                        @JsonProperty(value = "companyName") String companyName,
                                        @JsonProperty(value = "representativeName") String representativeName,
                                        @JsonProperty(value = "representativeSurname") String representativeSurname,
                                        @JsonProperty(value = "representativeEmailAddress") String representativeEmailAddress,
                                        @JsonProperty(value = "representativePhoneNumber") String representativePhoneNumber) {
        super(password);
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.representativeName = representativeName;
        this.representativeSurname = representativeSurname;
        this.representativeEmailAddress = representativeEmailAddress;
        this.representativePhoneNumber = representativePhoneNumber;
    }
}
