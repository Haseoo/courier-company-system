package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Value
@Validated
public class ReceiverInfoCommandData {

    @NotBlank(message = "Receiver name cannot be empty")
    private String name;
    @NotBlank(message = "Surname surname cannot be empty")
    private String surname;
    private String emailAddress;
    @NotBlank(message = "Receiver phone number cannot be empty")
    private String phoneNumber;

    @JsonCreator
    public ReceiverInfoCommandData(@JsonProperty(value = "name", required = true) String name,
                                   @JsonProperty(value = "surname", required = true) String surname,
                                   @JsonProperty(value = "emailAddress", required = true) String emailAddress,
                                   @JsonProperty(value = "phoneNumber", required = true) String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

}
