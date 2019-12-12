package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;

public class ParcelTypeDataToView extends MapperConverter<ParcelTypeData, ParcelTypeView> {
    @Override
    public ParcelTypeView convert(ParcelTypeData parcelTypeData) {
        return ParcelTypeView
                .builder()
                    .name(parcelTypeData.getName())
                    .description(parcelTypeData.getDescription())
                    .price(parcelTypeData.getPrice())
                    .active(parcelTypeData.getActive())
                .build();
    }
}
