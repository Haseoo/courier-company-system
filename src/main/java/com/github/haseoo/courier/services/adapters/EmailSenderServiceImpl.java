package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class EmailSenderServiceImpl {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;


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
