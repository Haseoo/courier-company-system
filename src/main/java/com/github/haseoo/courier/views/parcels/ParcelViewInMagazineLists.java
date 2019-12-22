package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import com.github.haseoo.courier.views.places.AddressView;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import com.github.haseoo.courier.views.users.clients.ClientView;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ParcelViewInMagazineLists {
    private Long id;
    private ParcelTypeView parcelType;
    private AddressView deliveryAddress;
    private AddressView senderAddress;
    private ClientView sender;
    private ReceiverInfoView receiverContactData;
    private LocalDate expectedDeliveryTime;
    private Boolean priority;
    private BigDecimal parcelFee;
    private Boolean paid;
    private Boolean dateMoved;
    private BigDecimal effectivePrice;
    private ParcelStateType currentState;

    public static ParcelViewInMagazineLists of(ParcelData parcelData) {
        return ParcelViewInMagazineLists
                .builder()
                .id(parcelData.getId())
                .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                .deliveryAddress(AddressView.of(parcelData.getDeliveryAddress()))
                .senderAddress(AddressView.of(parcelData.getSenderAddress()))
                .sender(ClientView.ofWithoutParcels(parcelData.getSender()))
                .receiverContactData(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                .expectedDeliveryTime(parcelData.getExpectedDeliveryTime())
                .priority(parcelData.getPriority())
                .parcelFee(parcelData.getParcelFee())
                .paid(parcelData.getPaid())
                .dateMoved(parcelData.getDateMoved())
                .effectivePrice(parcelData.getEffectivePrice())
                .currentState(parcelData.getCurrentState().getState())
                .build();
    }
}
