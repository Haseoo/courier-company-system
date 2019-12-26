package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.enums.ParcelStateType;
import lombok.Value;

@Value
public class ParcelChangeStateForCourierCommandData {
    private ParcelStateType newState;
    private Long courierId;
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
