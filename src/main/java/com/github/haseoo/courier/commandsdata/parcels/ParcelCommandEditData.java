package com.github.haseoo.courier.commandsdata.parcels;

import com.github.haseoo.courier.commandsdata.places.AddressCommandData;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ParcelCommandEditData {
    private AddressCommandData deliveryAddress;
    private AddressCommandData senderAddress;
    private ReceiverInfoCommandData receiverContactData;
    private BigDecimal parcelFee;
}
