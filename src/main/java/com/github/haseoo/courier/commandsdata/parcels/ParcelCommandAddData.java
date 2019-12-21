package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.places.AddressCommandData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ParcelCommandAddData {
    private Long parcelTypeId;
    private AddressCommandData deliveryAddress;
    private AddressCommandData senderAddress;
    private BigDecimal parcelFee;
    private Long senderId;
    private ReceiverInfoCommandData receiverContactData;
    private Boolean priority;

    @JsonCreator
    public ParcelCommandAddData(@JsonProperty(value = "parcelTypeId", required = true) Long parcelTypeId,
                                @JsonProperty(value = "deliveryAddress", required = true) AddressCommandData deliveryAddress,
                                @JsonProperty(value = "senderAddress", required = true) AddressCommandData senderAddress,
                                @JsonProperty(value = "parcelFee", required = true) BigDecimal parcelFee,
                                @JsonProperty(value = "senderId", required = true) Long senderId,
                                @JsonProperty(value = "receiverContactData", required = true) ReceiverInfoCommandData receiverContactData,
                                @JsonProperty(value = "priority", required = true) Boolean priority) {
        this.parcelTypeId = parcelTypeId;
        this.deliveryAddress = deliveryAddress;
        this.senderAddress = senderAddress;
        this.parcelFee = parcelFee;
        this.senderId = senderId;
        this.receiverContactData = receiverContactData;
        this.priority = priority;
    }
}
