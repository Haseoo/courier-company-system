package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
public class ReceiverInfoCommandData {

    private String name;
    private String surname;
    private String emailAddress;
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
