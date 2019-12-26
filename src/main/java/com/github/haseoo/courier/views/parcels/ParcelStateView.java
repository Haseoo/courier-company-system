package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.servicedata.parcels.ParcelStateData;
import com.github.haseoo.courier.views.places.MagazineWithoutListsView;
import com.github.haseoo.courier.views.users.employees.CourierWithoutListView;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@NoArgsConstructor(access = PRIVATE)
public abstract class ParcelStateView {
    private ParcelStateType stateType;
    private LocalDateTime changeTime;

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ClientParcelStateView extends ParcelStateView {
        private String city;

        private static ClientParcelStateView of(ParcelStateData data) {
            return ClientParcelStateView
                    .builder()
                    .stateType(data.getState())
                    .changeTime(data.getChangeDate())
                    .city(((data.getMagazine() != null) ?
                            data.getMagazine().getAddress().getCity() : null))
                    .build();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ParcelStateFullView extends ParcelStateView {
        private CourierWithoutListView courier;
        private MagazineWithoutListsView magazine;

        private static ParcelStateFullView of(ParcelStateData data) {
            return ParcelStateFullView
                    .builder()
                    .stateType(data.getState())
                    .changeTime(data.getChangeDate())
                    .courier(((data.getCourierData() != null) ?
                            CourierWithoutListView.of(data.getCourierData()) : null))
                    .magazine(((data.getMagazine() != null) ?
                            MagazineWithoutListsView.of(data.getMagazine()) : null))
                    .build();
        }
    }

    public static ParcelStateView of(ParcelStateData data, UserType userType) {
        switch (userType) {
            case LOGISTICIAN:
            case ADMIN:
            case COURIER:
                return ParcelStateFullView.of(data);
            case COMPANY_CLIENT:
            case INDIVIDUAL_CLIENT:
            default:
                return ClientParcelStateView.of(data);
        }
    }
}
