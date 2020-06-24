package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Value
@Validated
public class ParcelTypeCommandAddData {
    @NotBlank(message = "Parcel type name is empty")
    private String name;
    private String description;
    @PositiveOrZero(message = "Parcel type price cannot be negative or not set")
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
