package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeEditOperationData {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;

    public static ParcelTypeEditOperationData of(ParcelTypeCommandEditData commandData) {
        return new ParcelTypeEditOperationData(
                commandData.getName(),
                commandData.getDescription(),
                commandData.getPrice(),
                commandData.getActive()
        );
    }
}
