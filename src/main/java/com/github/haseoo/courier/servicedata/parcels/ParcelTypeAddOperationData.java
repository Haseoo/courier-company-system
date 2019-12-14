package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class ParcelTypeAddOperationData {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal price;
    @NonNull
    private Boolean active;

    public static ParcelTypeAddOperationData of(ParcelTypeCommandAddData commandData) {
        return new ParcelTypeAddOperationData(
                commandData.getName(),
                commandData.getDescription(),
                commandData.getPrice(),
                true
        );
    }
}
