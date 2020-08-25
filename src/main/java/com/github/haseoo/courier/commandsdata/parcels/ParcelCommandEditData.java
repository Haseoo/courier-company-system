package com.github.haseoo.courier.commandsdata.parcels;

import com.github.haseoo.courier.commandsdata.places.AddressCommandData;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Value
public class ParcelCommandEditData {
    @Valid
    @NotNull(message = "Address cannot be null")
    private AddressCommandData deliveryAddress;
    @Valid
    @NotNull(message = "Address cannot be null")
    private AddressCommandData senderAddress;
    @Valid
    @NotNull(message = "Receiver info cannot be null")
    private ReceiverInfoCommandData receiverContactData;
    @PositiveOrZero(message = "Parcel fee cannot be negative or not set")
    private BigDecimal parcelFee;
}
