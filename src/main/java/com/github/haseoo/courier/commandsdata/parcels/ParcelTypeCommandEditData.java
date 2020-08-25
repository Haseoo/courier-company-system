package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Value
@Validated
public class ParcelTypeCommandEditData {
    @NotBlank(message = "Parcel type name cannot be empty")
    private String name;
    private String description;
    @PositiveOrZero(message = "Parcel type price cannot be negative or not set")
    private BigDecimal price;
    @NotNull(message = "Parcel type must be active or not")
    private Boolean active;

    @JsonCreator
    public ParcelTypeCommandEditData(@JsonProperty(value = "name") String name,
                                     @JsonProperty(value = "description") String description,
                                     @JsonProperty(value = "price") BigDecimal price,
                                     @JsonProperty(value = "isActive") Boolean active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
    }
}
