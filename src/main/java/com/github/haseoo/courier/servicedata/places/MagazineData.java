package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class MagazineData {
    private Long id;
    private AddressData address;
    private Boolean active;
    private List<LogisticianData> logisticians;

    public static MagazineData of (MagazineModel magazineModel) {
        return MagazineData
                .builder()
                .id(magazineModel.getId())
                .address(AddressData.of(magazineModel.getAddress()))
                .active(magazineModel.getActive())
                .logisticians(((magazineModel.getLogisticians() != null) ? magazineModel
                        .getLogisticians()
                        .stream()
                        .map(LogisticianData::of)
                        .collect(Collectors.toList()) : new ArrayList<>()))
                .build();
    }
}
