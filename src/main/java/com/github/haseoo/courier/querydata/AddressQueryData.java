package com.github.haseoo.courier.querydata;

import com.github.haseoo.courier.servicedata.address.AddressOperationData;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddressQueryData {
    private String city;
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String flatNumber;

    public static AddressQueryData of(AddressOperationData addressOperationData) {
        return AddressQueryData
                .builder()
                .buildingNumber(addressOperationData.getBuildingNumber())
                .city(addressOperationData.getCity())
                .flatNumber(addressOperationData.getFlatNumber())
                .postalCode(addressOperationData.getPostalCode())
                .street(addressOperationData.getStreet())
                .build();
    }
}
