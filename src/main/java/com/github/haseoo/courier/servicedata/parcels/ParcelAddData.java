package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandAddData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Value
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParcelAddData {
    @NonNull
    private Long parcelTypeId;
    @NonNull
    private AddressOperationData deliveryAddress;
    @NonNull
    private AddressOperationData senderAddress;
    @NonNull
    private BigDecimal parcelFee;
    @NonNull
    private Long senderId;
    @NonNull
    private ReceiverInfoOperationData receiverContactData;
    @NonNull
    private Boolean priority;

    public static ParcelAddData of(ParcelCommandAddData addData) {
        return ParcelAddData
                .builder()
                .parcelTypeId(addData.getParcelTypeId())
                .deliveryAddress(AddressOperationData.of(addData.getDeliveryAddress()))
                .senderAddress(AddressOperationData.of(addData.getSenderAddress()))
                .parcelFee(addData.getParcelFee())
                .senderId(addData.getSenderId())
                .receiverContactData(ReceiverInfoOperationData.of(addData.getReceiverContactData()))
                .priority(addData.getPriority())
                .build();
    }
}
