package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ParcelStateData {
    private ParcelStateType state;
    private ParcelData parcel;
    private LocalDateTime changeDate;
    private MagazineData magazine;
    private CourierData courierData;

    public static ParcelStateData of(ParcelStateRecord parcelStateRecord) {
        return ParcelStateData
                .builder()
                .state(parcelStateRecord.getState())
                .changeDate(parcelStateRecord.getChangeDate())
                .parcel(ParcelData.of(parcelStateRecord.getParcel()))
                .magazine(MagazineData.ofWithoutLists(parcelStateRecord.getMagazine()))
                .courierData(CourierData.ofWithoutList(parcelStateRecord.getCourier()))
                .build();
    }

    public static ParcelStateData ofWithoutParcel(ParcelStateRecord parcelStateRecord) {
        return ParcelStateData
                .builder()
                .state(parcelStateRecord.getState())
                .changeDate(parcelStateRecord.getChangeDate())
                .magazine(MagazineData.ofWithoutLists(parcelStateRecord.getMagazine()))
                .courierData(CourierData.ofWithoutList(parcelStateRecord.getCourier()))
                .build();
    }
}
