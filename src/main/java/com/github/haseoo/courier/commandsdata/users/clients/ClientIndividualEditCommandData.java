package com.github.haseoo.courier.commandsdata.users.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserEditCommandData;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Validated
@Value
public class ClientIndividualEditCommandData extends UserEditCommandData {
    private String emailAddress;
    private String phoneNumber;
    private String name;
    private String surname;

    public ClientIndividualEditCommandData(@JsonProperty(value = "password") char[] password,
                                           @JsonProperty(value = "emailAddress") String emailAddress,
                                           @JsonProperty(value = "phoneNumber") String phoneNumber,
                                           @JsonProperty(value = "name") String name,
                                           @JsonProperty(value = "surname") String surname) {
        super(password);
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.surname = surname;
    }
}
