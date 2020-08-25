package com.github.haseoo.courier.views.places;

import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.views.parcels.ParcelViewInMagazineLists;
import com.github.haseoo.courier.views.users.employees.LogisticianWithoutMagazineView;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Getter
public class MagazineView {
    private Long id;
    private AddressView address;
    private Boolean active;
    private List<LogisticianWithoutMagazineView> logisticians;
    private List<ParcelViewInMagazineLists> parcels;

    public static MagazineView of(MagazineData magazineData) {
        return MagazineView
                .builder()
                .id(magazineData.getId())
                .address(AddressView.of(magazineData.getAddress()))
                .active(magazineData.getActive())
                .logisticians(magazineData.getLogisticians()
                        .stream()
                        .map(LogisticianWithoutMagazineView::of)
                        .collect(Collectors.toList()))
                .parcels(((magazineData.getParcels() != null) ?
                        magazineData.getParcels()
                                .stream()
                                .map(ParcelViewInMagazineLists::of)
                                .collect(Collectors.toList()) : new ArrayList<>()))
                .build();
    }
}
