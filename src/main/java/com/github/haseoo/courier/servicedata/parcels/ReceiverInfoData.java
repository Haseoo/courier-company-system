package com.github.haseoo.courier.servicedata.parcels;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiverInfoData extends ReceiverInfoOperationData {
    private Long id;
}
