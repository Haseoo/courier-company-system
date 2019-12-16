package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.servicedata.places.AddressData;

public class AddressModelToData extends MapperConverter<AddressModel, AddressData> {
    @Override
    protected AddressData convert(AddressModel addressModel) {
        return AddressData
                .builder()
                .id(addressModel.getId())
                .buildingNumber(addressModel.getBuildingNumber())
                .city(addressModel.getCity())
                .flatNumber(addressModel.getFlatNumber())
                .postalCode(addressModel.getPostalCode())
                .street(addressModel.getStreet())
                .build();
    }
}
