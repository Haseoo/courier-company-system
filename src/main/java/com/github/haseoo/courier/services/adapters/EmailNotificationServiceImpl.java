package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.services.ports.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private EmailSenderServiceImpl emailService;

    public void sendNotificationToReceiver(ParcelData parcelData, String information){
        try {
            MailModel mail = new MailModel();
            mail.setFrom(mailFrom);
            mail.setMailTo(parcelData.getReceiverContactData().getEmailAddress());
            mail.setSubject("JanuszeX Courier Company - send confirmation");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("information", information);
            model.put("name", parcelData.getReceiverContactData().getName());
            model.put("parcelId", parcelData.getId());
            mail.setProps(model);

            emailService.sendEmail(mail);
        }catch(MessagingException exception){
            log.error(exception.toString());
        }
    }
}
