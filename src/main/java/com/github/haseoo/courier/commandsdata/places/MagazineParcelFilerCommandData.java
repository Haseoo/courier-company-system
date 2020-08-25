package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.enums.MagazineParcelFilterType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class MagazineParcelFilerCommandData {
    @JsonCreator
    public MagazineParcelFilerCommandData(@JsonProperty(value = "type", required = true) MagazineParcelFilterType type) {
        this.type = type;
    }

    @NotNull(message = "Filter type cannot be null")
    private MagazineParcelFilterType type;
}
