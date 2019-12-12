package com.github.haseoo.courier.views.parcels.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ParcelTypeView {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
