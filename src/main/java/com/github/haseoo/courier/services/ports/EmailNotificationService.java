package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;

public interface EmailNotificationService {
    public void sendNotificationToReceiver(ParcelData parcelData, String information);
}
