package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.MailModel;

public interface EmailSenderService {

    public void sendEmail(MailModel mailModel);
}
