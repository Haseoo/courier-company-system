package com.github.haseoo.courier.views.places;

import com.github.haseoo.courier.servicedata.places.AddressData;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@Getter
public class AddressView {
    private String city;
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String flatNumber;

    public static AddressView of(AddressData addressData) {
        return AddressView
                .builder()
                .city(addressData.getCity())
                .street(addressData.getStreet())
                .postalCode(addressData.getPostalCode())
                .buildingNumber(addressData.getBuildingNumber())
                .flatNumber(addressData.getFlatNumber())
                .build();
    }
}

