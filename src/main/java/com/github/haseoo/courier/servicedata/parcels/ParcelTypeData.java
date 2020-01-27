package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.models.ParcelTypeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<ParcelData> parcelsWithType;

    public static ParcelTypeData of(ParcelTypeModel parcelTypeModel) {
        return ParcelTypeData
                .builder()
                .id(parcelTypeModel.getId())
                .name(parcelTypeModel.getName())
                .description(parcelTypeModel.getDescription())
                .price(parcelTypeModel.getPrice())
                .active(parcelTypeModel.getActive())
                .parcelsWithType(((parcelTypeModel.getParcels() != null) ?
                        parcelTypeModel.getParcels()
                                .stream()
                                .map(ParcelData::ofWithoutStates)
                                .collect(Collectors.toList()) :
                        new ArrayList<>()))
                .build();
    }

    public static ParcelTypeData ofWithoutList(ParcelTypeModel parcelTypeModel) {
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
