package com.github.haseoo.courier.commandsdata.places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import static com.github.haseoo.courier.utilities.Utils.validateStringField;

@Value
@Validated
public class AddressCommandData {
    private String city;
    private String street;

    @JsonCreator
    public AddressCommandData(@JsonProperty(value = "city", required = true) String city,
                              @JsonProperty(value = "street", required = true) String street,
                              @JsonProperty(value = "postalCode", required = true) String postalCode,
                              @JsonProperty(value = "buildingNumber", required = true) String buildingNumber,
                              @JsonProperty(value = "flatNumber", required = true) String flatNumber) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    private String postalCode;
    private String buildingNumber;
    private String flatNumber;
}
