package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;

public class ParcelTypeModelToData extends MapperConverter<ParcelTypeModel, ParcelTypeData> {
    @Override
    public ParcelTypeData convert(ParcelTypeModel parcelTypeModel) {
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
