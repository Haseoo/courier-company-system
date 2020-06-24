package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class ParcelDateMoveCommandData {
    @NotNull(message = "New date cannot be null")
    private LocalDate newDate;
    @NotNull(message = "Pin must be given")
    private char[] pin;

    @JsonCreator
    public ParcelDateMoveCommandData(@JsonProperty(value = "newDate", required = true) LocalDate newDate,
                                     @JsonProperty(value = "parcelPin", required = true) char[] pin) {
        this.newDate = newDate;
        this.pin = pin;
    }
}
