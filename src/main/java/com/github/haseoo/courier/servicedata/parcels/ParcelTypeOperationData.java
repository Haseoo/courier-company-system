package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeOperationData {
    private String name;
    private String description;
    private BigDecimal price;
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
