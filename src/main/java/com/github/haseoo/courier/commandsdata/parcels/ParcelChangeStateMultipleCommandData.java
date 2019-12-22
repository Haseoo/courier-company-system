package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.util.List;

@Value
public class ParcelChangeStateMultipleCommandData {
    private List<Long> parcelsIds;

    @JsonCreator
    public ParcelChangeStateMultipleCommandData(List<Long> parcelsIds) {
        this.parcelsIds = parcelsIds;
    }
}
