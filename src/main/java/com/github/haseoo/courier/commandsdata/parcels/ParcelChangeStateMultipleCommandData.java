package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
public class ParcelChangeStateMultipleCommandData {
    @NotEmpty(message = "At least one parcel id must be given")
    private List<Long> parcelsIds;

    @JsonCreator
    public ParcelChangeStateMultipleCommandData(@JsonProperty(value = "parcelsIds", required = true) List<Long> parcelsIds) {
        this.parcelsIds = parcelsIds;
    }
}
