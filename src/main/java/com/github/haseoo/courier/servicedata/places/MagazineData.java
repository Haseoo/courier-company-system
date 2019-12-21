package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.ParcelStateType.IN_MAGAZINE;
import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class MagazineData {
    private Long id;
    private AddressData address;
    private Boolean active;
    private List<LogisticianData> logisticians;
    private List<ParcelData> parcels;

    public static MagazineData of(MagazineModel magazineModel) {
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
                .parcels(((magazineModel.getParcelStates() != null) ? magazineModel
                        .getParcelStates()
                        .stream()
                        .map(ParcelStateRecord::getParcel)
                        .map(ParcelData::of)
                        .filter(parcelData -> parcelData.getCurrentState().getState() == IN_MAGAZINE)
                        .collect(Collectors.toList())
                        : new ArrayList<>()))
                .build();
    }

    public static MagazineData ofWithoutLists(MagazineModel magazineModel) {
        if (magazineModel != null) {
            return MagazineData
                    .builder()
                    .id(magazineModel.getId())
                    .address(AddressData.of(magazineModel.getAddress()))
                    .active(magazineModel.getActive())
                    .build();
        }
        return null;
    }
}
