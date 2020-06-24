package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.enums.ParcelStateType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ParcelChangeStateForCourierCommandData {
    @NotNull(message = "New state must be set")
    private ParcelStateType newState;
    @NotNull(message = "Courier id must be given")
    private Long courierId;
    @NotNull(message = "Paid status must be given")
    private Boolean wasPaid;

    @JsonCreator
    public ParcelChangeStateForCourierCommandData(@JsonProperty(value = "newState", required = true) ParcelStateType newState,
                                                  @JsonProperty(value = "courierId", required = true) Long courierId,
                                                  @JsonProperty(value = "wasPaid", defaultValue = "false") boolean wasPaid) {
        this.newState = newState;
        this.courierId = courierId;
        this.wasPaid = wasPaid;
    }

}
