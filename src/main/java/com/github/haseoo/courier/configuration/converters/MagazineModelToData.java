package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.servicedata.places.MagazineData;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MagazineModelToData extends MapperConverter<MagazineModel, MagazineData> {
    @Override
    protected MagazineData convert(MagazineModel magazineModel) {
        AddressModelToData addressModelToData = new AddressModelToData();
        LogisticianModelToData logisticianModelToData = new LogisticianModelToData();
        return MagazineData
                .builder()
                .id(magazineModel.getId())
                .address(addressModelToData.convert(magazineModel.getAddress()))
                .active(magazineModel.getActive())
                .logisticians(((magazineModel.getLogisticians() != null) ? magazineModel
                        .getLogisticians()
                        .stream()
                        .map(logisticianModelToData::convert)
                        .collect(Collectors.toList()) : new ArrayList<>()))
                .build();
    }
}
