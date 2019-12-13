package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeOperationData {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal price;
    @NonNull
    private Boolean active;

    //TODO do mapera
    public static ParcelTypeOperationData of(ParcelTypeCommandAddData commandData) {
        return new ParcelTypeOperationData(
                commandData.getName(),
                commandData.getDescription(),
                commandData.getPrice(),
                true
        );
    }

    public static ParcelTypeOperationData of(ParcelTypeCommandEditData commandData) {
        return new ParcelTypeOperationData(
                commandData.getName(),
                commandData.getDescription(),
                commandData.getPrice(),
                commandData.getActive()
        );
    }
}
