package com.github.haseoo.courier.views.places;

import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Getter
public class MagazineView {
    private Long id;
    private AddressView address;
    private Boolean active;
    private List<Long> logisticiansIds;

    public static MagazineView of(MagazineData magazineData) {
        return MagazineView
                .builder()
                .id(magazineData.getId())
                .address(AddressView.of(magazineData.getAddress()))
                .active(magazineData.getActive())
                .logisticiansIds(magazineData.getLogisticians()
                        .stream()
                        .map(LogisticianData::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}