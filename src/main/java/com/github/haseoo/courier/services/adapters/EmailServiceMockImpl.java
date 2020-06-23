package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.services.ports.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("!prod")
public class EmailServiceMockImpl implements EmailService {

    @Override
    public void sentNotificationToSender(ParcelData parcelData) {
        log.info(String.format("Sent to sender %s id %s pin %s", parcelData.getSender().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));
    }

    @Override
    public void sentNotificationToReceiver(ParcelData parcelData) {
        log.info(String.format("Sent to receiver %s id %s pin %s", parcelData.getReceiverContactData().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));
    }


    @Override
    public void sentReturnNotification(ParcelData parcelData) {
        log.info(String.format("Sent return to sender %s id %s pin %s", parcelData.getSender().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));
    }
}
