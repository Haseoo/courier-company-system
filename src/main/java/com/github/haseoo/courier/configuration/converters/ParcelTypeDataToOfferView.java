package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeOfferView;

public class ParcelTypeDataToOfferView extends MapperConverter<ParcelTypeData, ParcelTypeOfferView> {
    @Override
    public ParcelTypeOfferView convert(ParcelTypeData parcelTypeData) {
        return ParcelTypeOfferView
                .builder()
                .name(parcelTypeData.getName())
                .description(parcelTypeData.getDescription())
                .price(parcelTypeData.getPrice())
                .active(parcelTypeData.getActive())
                .build();
    }
}
