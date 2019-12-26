package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ParcelPickupCommandData {
    private Long parcelsId;
    private boolean wasPaid;

    @JsonCreator
    public ParcelPickupCommandData(@JsonProperty(value = "parcelsIds", required = true) Long parcelsId,
                                   @JsonProperty(value = "wasPaid", defaultValue = "false") boolean wasPaid) {
        this.parcelsId = parcelsId;
        this.wasPaid = wasPaid;
    }
}
