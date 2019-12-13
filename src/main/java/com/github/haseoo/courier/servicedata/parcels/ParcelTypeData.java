package com.github.haseoo.courier.servicedata.parcels;

import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeData {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
