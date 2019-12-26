package com.github.haseoo.courier.views.places;

import com.github.haseoo.courier.servicedata.places.MagazineData;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Getter
public class MagazineWithoutListsView {
    private Long id;
    private AddressView address;
    private Boolean active;

    public static MagazineWithoutListsView of(MagazineData magazineData) {
        return MagazineWithoutListsView
                .builder()
                .id(magazineData.getId())
                .address(AddressView.of(magazineData.getAddress()))
                .active(magazineData.getActive())
                .build();
    }
}
