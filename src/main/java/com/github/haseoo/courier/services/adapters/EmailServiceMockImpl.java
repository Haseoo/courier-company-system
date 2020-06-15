package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.services.ports.EmailSenderService;
import com.github.haseoo.courier.services.ports.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceMockImpl implements EmailService {

    @Autowired
    private EmailSenderServiceImpl emailService;

    @Override
    public void sentNotificationToSender(ParcelData parcelData){
        log.info(String.format("Sent to sender %s id %s pin %s", parcelData.getSender().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));

        try {
            MailModel mail = new MailModel();
            mail.setFrom("yourmailid@email.com");//replace with your desired email
            mail.setMailTo("tomail@email.com");//replace with your desired email
            mail.setSubject("Email with Spring boot and thymeleaf template!");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", "Developer!");
            model.put("location", "United States");
            model.put("sign", "Java Developer");
            mail.setProps(model);

            emailService.sendEmail(mail);
        }catch (MessagingException exception){
            log.error(exception.toString());
        }
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
