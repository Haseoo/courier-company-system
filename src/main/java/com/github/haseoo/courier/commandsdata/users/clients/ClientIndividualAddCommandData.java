package com.github.haseoo.courier.commandsdata.users.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserAddCommandData;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class ClientIndividualAddCommandData extends UserAddCommandData {
    private String emailAddress;
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    private String pesel;

    @JsonCreator
    public ClientIndividualAddCommandData(@JsonProperty(value = "userName", required = true) String userName,
                                          @JsonProperty(value = "password", required = true) char[] password,
                                          @JsonProperty(value = "emailAddress", required = true) String emailAddress,
                                          @JsonProperty(value = "phoneNumber", required = true) String phoneNumber,
                                          @JsonProperty(value = "name", required = true) String name,
                                          @JsonProperty(value = "surname", required = true) String surname,
                                          @JsonProperty(value = "pesel", required = true) String pesel) {
        super(userName, password);
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }
}
