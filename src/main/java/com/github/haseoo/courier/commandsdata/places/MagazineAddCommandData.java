package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
public class MagazineAddCommandData {
    private AddressCommandData address;

    @JsonCreator
    public MagazineAddCommandData(@JsonProperty(value = "address", required = true) AddressCommandData address) {
        this.address = address;
    }
}
