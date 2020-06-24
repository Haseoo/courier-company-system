package com.github.haseoo.courier.commandsdata.users.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserEditCommandData;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Validated
@Value
public class ClientIndividualEditCommandData extends UserEditCommandData {
    private String emailAddress;
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @JsonCreator
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
