package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import com.github.haseoo.courier.services.ports.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailSenderServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public void sendEmail(MailModel mailModel) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mailModel.getProps());

        String html = templateEngine.process("mail", context);

        helper.setTo(mailModel.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mailModel.getSubject());
        helper.setFrom(mailModel.getFrom());

        emailSender.send(message);
    }

}
