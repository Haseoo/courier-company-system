package com.github.haseoo.courier.repositories.querydata;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressQueryData {
    private String city;
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String flatNumber;
}
