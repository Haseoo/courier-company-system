package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ParcelPickupCommandData {
    @NotNull(message = "Parcel id must be specified")
    private Long parcelsId;
    @NotNull
    private boolean wasPaid;

    @JsonCreator
    public ParcelPickupCommandData(@JsonProperty(value = "parcelsIds", required = true) Long parcelsId,
                                   @JsonProperty(value = "wasPaid", defaultValue = "false") boolean wasPaid) {
        this.parcelsId = parcelsId;
        this.wasPaid = wasPaid;
    }
}
