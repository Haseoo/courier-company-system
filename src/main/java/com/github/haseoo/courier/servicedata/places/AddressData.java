package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.models.AddressModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Value
@AllArgsConstructor(access = PRIVATE)
public class AddressData extends AddressOperationData {
    private Long id;

    public static AddressData of(AddressModel addressModel) {
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
