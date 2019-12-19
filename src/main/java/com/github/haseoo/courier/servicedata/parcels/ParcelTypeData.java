package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.models.ParcelTypeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeData {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;

    public static ParcelTypeData of(ParcelTypeModel parcelTypeModel) {
        return ParcelTypeData
                .builder()
                .id(parcelTypeModel.getId())
                .name(parcelTypeModel.getName())
                .description(parcelTypeModel.getDescription())
                .price(parcelTypeModel.getPrice())
                .active(parcelTypeModel.getActive())
                .build();
    }
}
