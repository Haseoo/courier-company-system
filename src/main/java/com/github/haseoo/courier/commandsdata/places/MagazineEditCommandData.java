package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
public class MagazineEditCommandData {
    private AddressCommandData address;
    private Boolean active;

    @JsonCreator
    public MagazineEditCommandData(@JsonProperty(value = "address") AddressCommandData address,
                                   @JsonProperty(value = "active") Boolean active) {
        this.address = address;
        this.active = active;
    }
}
