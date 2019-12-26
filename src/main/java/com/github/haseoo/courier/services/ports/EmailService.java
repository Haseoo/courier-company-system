package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;

public interface EmailService {
    void sentNotificationToSender(ParcelData parcelData);

    void sentNotificationToReceiver(ParcelData parcelData);

    void sentReturnNotification(ParcelData parcelData);
}
