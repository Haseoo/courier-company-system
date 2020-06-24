package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
@Validated
public class MagazineAddLogisiticiansCommandData {
    @NotEmpty(message = "At least one id must be given")
    private List<Long> logisiticiansIds;

    public MagazineAddLogisiticiansCommandData(@JsonProperty(value = "logisticiansIds", required = true) List<Long> logisiticiansIds) {
        this.logisiticiansIds = logisiticiansIds;
    }
}
