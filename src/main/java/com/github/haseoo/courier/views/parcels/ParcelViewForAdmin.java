package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import com.github.haseoo.courier.views.places.AddressView;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import com.github.haseoo.courier.views.users.clients.ClientView;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class ParcelViewForAdmin {
    private Long id;
    private char[] pin;
    private ParcelTypeView parcelType;
    private AddressView deliveryAddress;
    private AddressView senderAddress;
    private ClientView sender; //wo-parcels!!!!!!!!!!!!!!!!
    private ReceiverInfoView receiverContactData;
    private LocalDate expectedDeliveryTime;
    private Boolean priority;
    private BigDecimal parcelFee;
    private Boolean paid;
    private Boolean dateMoved;
    private BigDecimal effectivePrice;
    private List<ParcelStateViewForAdmin> states;
    private ParcelStateViewForAdmin currentState;

    public static ParcelViewForAdmin of(ParcelData parcelData) {
        return ParcelViewForAdmin
                .builder()
                .id(parcelData.getId())
                .pin(parcelData.getPin())
                .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                .deliveryAddress(AddressView.of(parcelData.getDeliveryAddress()))
                .senderAddress(AddressView.of(parcelData.getSenderAddress()))
                .sender(ClientView.ofWithoutParcels(parcelData.getSender()))
                .receiverContactData(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                .expectedDeliveryTime(parcelData.getExpectedCourierArrivalDate())
                .priority(parcelData.getPriority())
                .parcelFee(parcelData.getParcelFee())
                .paid(parcelData.getPaid())
                .dateMoved(parcelData.getDateMoved())
                .effectivePrice(parcelData.getEffectivePrice())
                .currentState(ParcelStateViewForAdmin.of(parcelData.getCurrentState()))
                .states(parcelData.getParcelStates()
                        .stream()
                        .map(ParcelStateViewForAdmin::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
