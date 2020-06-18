package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.commandsdata.places.MagazineEditCommandData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Value
@Builder(access = PUBLIC)
@AllArgsConstructor(access = PRIVATE)
public class MagazineEditOperationData {
    private AddressOperationData address;
    private Boolean active;

    public static MagazineEditOperationData of(MagazineEditCommandData commandData) {
        return MagazineEditOperationData
                .builder()
                .active(commandData.getActive())
                .address(AddressOperationData.of(commandData.getAddress()))
                .build();
    }
}
