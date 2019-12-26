package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ParcelDateMoveCommandData {
    private LocalDate newDate;
    private char[] pin;

    @JsonCreator
    public ParcelDateMoveCommandData(@JsonProperty(value = "newDate", required = true) LocalDate newDate,
                                     @JsonProperty(value = "parcelPin", required = true) char[] pin) {
        this.newDate = newDate;
        this.pin = pin;
    }
}
