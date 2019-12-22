package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import com.github.haseoo.courier.views.places.AddressView;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class ParcelViewAfterAddOrEdit {
    private Long id;
    private ParcelTypeView parcelType;
    private AddressView deliveryAddress;
    private AddressView senderAddress;
    private ReceiverInfoView receiverContactData;
    private Boolean priority;
    private BigDecimal parcelFee;

    public static ParcelViewAfterAddOrEdit of(ParcelData parcelData) {
        return ParcelViewAfterAddOrEdit
                .builder()
                .id(parcelData.getId())
                .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                .deliveryAddress(AddressView.of(parcelData.getDeliveryAddress()))
                .senderAddress(AddressView.of(parcelData.getSenderAddress()))
                .receiverContactData(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                .priority(parcelData.getPriority())
                .parcelFee(parcelData.getEffectivePrice())
                .build();
    }
}
