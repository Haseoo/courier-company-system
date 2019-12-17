package com.github.haseoo.courier.views.parcels.type;

import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
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

    public static ParcelTypeOfferView of(ParcelTypeData parcelTypeData) {
        return ParcelTypeOfferView
                .builder()
                .name(parcelTypeData.getName())
                .description(parcelTypeData.getDescription())
                .price(parcelTypeData.getPrice())
                .active(parcelTypeData.getActive())
                .build();
    }
}
