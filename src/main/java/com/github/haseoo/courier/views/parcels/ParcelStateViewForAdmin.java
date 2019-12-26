package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.servicedata.parcels.ParcelStateData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParcelStateViewForAdmin {
    private Long parcelId;
    private Long magazineId;
    private Long courierId;
    private ParcelStateType parcelStateType;
    private LocalDateTime changeTime;

    public static ParcelStateViewForAdmin of(ParcelStateData data) {
        return ParcelStateViewForAdmin
                .builder()
                .parcelId(data.getParcel().getId())
                .magazineId(((data.getMagazine() != null) ?
                        data.getMagazine().getId() : null))
                .courierId(((data.getCourierData() != null) ?
                        data.getCourierData().getId() : null))
                .parcelStateType(data.getState())
                .changeTime(data.getChangeDate())
                .build();
    }
}
