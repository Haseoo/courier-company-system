package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.commandsdata.places.MagazineAddCommandData;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder(access = PRIVATE)
public class MagazineAddOperationData {
    @NonNull
    private AddressOperationData address;

    public static MagazineAddOperationData of(MagazineAddCommandData commandData) {
        return MagazineAddOperationData
                .builder()
                .address(AddressOperationData.of(commandData.getAddress()))
                .build();
    }
}
