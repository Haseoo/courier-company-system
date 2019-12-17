package com.github.haseoo.courier.views.parcels.type;

import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class ParcelTypeView extends ParcelTypeOfferView {
    private Long id;

    public static ParcelTypeView of(ParcelTypeData parcelTypeData) {
        return ParcelTypeView.builder()
                    .id(parcelTypeData.getId())
                    .active(parcelTypeData.getActive())
                    .description(parcelTypeData.getDescription())
                    .name(parcelTypeData.getName())
                    .price(parcelTypeData.getPrice())
                .build();
    }
}
