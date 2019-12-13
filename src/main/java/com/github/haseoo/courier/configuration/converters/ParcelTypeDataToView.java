package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;

public class ParcelTypeDataToView extends MapperConverter<ParcelTypeData, ParcelTypeView> {
    @Override
    public ParcelTypeView convert(ParcelTypeData parcelTypeData) {
        return ParcelTypeView.builder()
                .id(parcelTypeData.getId())
                .active(parcelTypeData.getActive())
                .description(parcelTypeData.getDescription())
                .name(parcelTypeData.getName())
                .price(parcelTypeData.getPrice())
                .build();
    }
}
