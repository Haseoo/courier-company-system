package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.MailGenerator.getMailModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class EmailSenderServiceImplTest {
    @Mock
    private JavaMailSenderImpl emailSender;

    @Mock
    private ITemplateEngine templateEngine;

    @InjectMocks
    private EmailSenderServiceImpl sut;

    @Test
    void should_send_email() throws MessagingException {
        //given
        final String html = "<html></html>";

        MailModel mailModel = getMailModel();
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn(html);
        when(emailSender.createMimeMessage()).thenCallRealMethod();
        ArgumentCaptor<Context> contextArgumentCaptor = ArgumentCaptor.forClass(Context.class);
        //when
        sut.sendEmail(mailModel);
        verify(templateEngine).process(eq("mail"), contextArgumentCaptor.capture());
        verify(emailSender).send(any(MimeMessage.class));
        //then
        Assertions.assertThat(contextArgumentCaptor.getValue().getVariable("testprop")).isEqualTo("testprop");
    }


}