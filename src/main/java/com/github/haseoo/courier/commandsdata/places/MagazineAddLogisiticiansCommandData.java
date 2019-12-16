package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Value
@Validated
public class MagazineAddLogisiticiansCommandData {
    private List<Long> logisiticiansIds;

    public MagazineAddLogisiticiansCommandData(@JsonProperty(value = "logisticiansIds", required = true) List<Long> logisiticiansIds) {
        this.logisiticiansIds = logisiticiansIds;
    }
}
