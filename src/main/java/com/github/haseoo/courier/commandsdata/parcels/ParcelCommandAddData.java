package com.github.haseoo.courier.commandsdata.parcels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.places.AddressCommandData;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Value
public class ParcelCommandAddData {
    @NotNull(message = "Parcel must have type")
    private Long parcelTypeId;
    @Valid
    @NotNull(message = "Address cannot be null")
    private AddressCommandData deliveryAddress;
    @Valid
    @NotNull(message = "Address cannot be null")
    private AddressCommandData senderAddress;
    @PositiveOrZero(message = "Parcel fee cannot be negative or not set")
    private BigDecimal parcelFee;
    @NotNull(message = "Parcel must have sender")
    private Long senderId;
    @Valid
    @NotNull(message = "Receiver info cannot be null")
    private ReceiverInfoCommandData receiverContactData;
    @NotNull(message = "Parcel priority must be specified")
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
