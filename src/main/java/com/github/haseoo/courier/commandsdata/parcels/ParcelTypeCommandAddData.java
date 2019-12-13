package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Validated
public class ParcelTypeCommandAddData {
    private String name;
    private String description;
    private BigDecimal price;

    @JsonCreator
    public ParcelTypeCommandAddData(@JsonProperty(value = "name", required = true) String name,
                                    @JsonProperty(value = "description", required = true) String description,
                                    @JsonProperty(value = "price", required = true) BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
