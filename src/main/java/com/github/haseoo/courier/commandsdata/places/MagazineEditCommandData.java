package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@Validated
public class MagazineEditCommandData {
    @Valid
    @NotNull(message = "Address cannot be null")
    private AddressCommandData address;
    @NotNull(message = "New status cannot be empty")
    private Boolean active;

    @JsonCreator
    public MagazineEditCommandData(@JsonProperty(value = "address") AddressCommandData address,
                                   @JsonProperty(value = "active") Boolean active) {
        this.address = address;
        this.active = active;
    }
}
