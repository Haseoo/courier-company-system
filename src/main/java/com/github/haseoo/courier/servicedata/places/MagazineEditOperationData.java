package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.commandsdata.places.MagazineEditCommandData;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = PRIVATE)
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
