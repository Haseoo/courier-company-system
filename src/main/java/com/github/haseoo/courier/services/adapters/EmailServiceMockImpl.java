package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.services.ports.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceMockImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private EmailSenderServiceImpl emailService;

    @Autowired
    private EmailNotificationServiceImpl emailNotificationService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void sentNotificationToSender(ParcelData parcelData) {
        log.info(String.format("Sent to sender %s id %s pin %s", parcelData.getSender().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));

        MailModel mail = new MailModel();
        mail.setFrom(mailFrom);
        mail.setMailTo(parcelData.getSender().getEmailAddress());
        mail.setSubject("JanuszeX Courier Company - send confirmation");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("information", "Our courier will pick up the parcel within 1 day.");
        model.put("name", parcelData.getSender().getUserName());
        model.put("parcelId", parcelData.getId());
        model.put("pin", String.valueOf(parcelData.getPin()));
        model.put("showPin", "active");
        mail.setProps(model);

        threadPoolTaskExecutor.execute(() -> {
            try {
                emailService.sendEmail(mail);
                emailNotificationService.sendNotificationToReceiver(parcelData, "Seller has registered your parcel.");
            } catch (MessagingException exception) {
                log.error(exception.toString());
            }
        });
    }

    @Override
    public void sentNotificationToReceiver(ParcelData parcelData) {
        log.info(String.format("Sent to receiver %s id %s pin %s", parcelData.getReceiverContactData().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));

        MailModel mail = new MailModel();
        mail.setFrom(mailFrom);
        mail.setMailTo(parcelData.getReceiverContactData().getEmailAddress());
        mail.setSubject("JanuszeX Courier Company - send confirmation");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("information", "A parcel is on the way to you!");
        model.put("name", parcelData.getReceiverContactData().getName());
        model.put("parcelId", parcelData.getId());
        model.put("pin", String.valueOf(parcelData.getPin()));
        model.put("showPin", "active");
        mail.setProps(model);

        threadPoolTaskExecutor.execute(() -> {
            try {
                emailService.sendEmail(mail);
            } catch (MessagingException exception) {
                log.error(exception.toString());
            }
        });
    }

    @Override
    public void sentReturnNotification(ParcelData parcelData) {
        log.info(String.format("Sent return to sender %s id %s pin %s", parcelData.getSender().getEmailAddress(),
                parcelData.getId(),
                String.valueOf(parcelData.getPin())));

        MailModel mail = new MailModel();
        mail.setFrom(mailFrom);
        mail.setMailTo(parcelData.getSender().getEmailAddress());
        mail.setSubject("JanuszeX Courier Company - parcel return tracking pin");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("information", "The customer just picked up the parcel.");
        model.put("name", parcelData.getSender().getUserName());
        model.put("parcelId", parcelData.getId());
        model.put("pin", String.valueOf(parcelData.getPin()));
        model.put("showPin", "active");
        mail.setProps(model);

        threadPoolTaskExecutor.execute(() -> {
            try {
                emailService.sendEmail(mail);
            } catch (MessagingException exception) {
                log.error(exception.toString());
            }
        });
    }
}
