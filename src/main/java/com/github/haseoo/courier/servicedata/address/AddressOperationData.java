package com.github.haseoo.courier.servicedata.address;

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
    private String flatNumber;
}
