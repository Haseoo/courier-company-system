package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.commandsdata.places.AddressCommandData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class AddressOperationData {
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private String postalCode;
    @NonNull
    private String buildingNumber;
    @NonNull
    private String flatNumber;

    public static AddressOperationData of(AddressCommandData commandData) {
        return AddressOperationData
                .builder()
                .city(commandData.getCity())
                .street(commandData.getStreet())
                .postalCode(commandData.getPostalCode())
                .buildingNumber(commandData.getBuildingNumber())
                .flatNumber(commandData.getFlatNumber())
                .build();
    }
}
