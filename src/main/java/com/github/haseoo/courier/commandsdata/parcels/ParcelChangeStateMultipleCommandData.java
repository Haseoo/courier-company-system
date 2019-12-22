package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class ParcelChangeStateMultipleCommandData {
    private List<Long> parcelsIds;

    @JsonCreator
    public ParcelChangeStateMultipleCommandData(@JsonProperty(value = "parcelsIds", required = true) List<Long> parcelsIds) {
        this.parcelsIds = parcelsIds;
    }
}
