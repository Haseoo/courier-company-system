package com.github.haseoo.courier.views.parcels.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
public class ParcelTypeOfferView {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
