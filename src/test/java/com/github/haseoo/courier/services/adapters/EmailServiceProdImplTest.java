package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.MailModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import java.util.Map;

import static com.github.haseoo.courier.testutlis.constants.Constants.TEST_SEND_EMAIL_ADDRESS;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelAtSender;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class EmailServiceProdImplTest {
    @Mock
    private EmailSenderServiceImpl emailService;

    @Mock
    private EmailNotificationServiceImpl emailNotificationService;

    @Mock
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @InjectMocks
    private EmailServiceProdImpl sut;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(sut,
                EmailServiceProdImpl.class,
                "mailFrom",
                TEST_SEND_EMAIL_ADDRESS,
                String.class);
    }


    @Test
    void should_send_notification_to_sender() throws MessagingException {
        //given
        doNothing().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        //when
        sut.sentNotificationToSender(in);
        verify(threadPoolTaskExecutor).execute(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        //then
        verify(emailNotificationService).sendNotificationToReceiver(eq(in), anyString());
        verify(emailService).sendEmail(any());
    }

    @Test
    void should_send_notification_to_receiver() throws MessagingException, InterruptedException {
        //given
        doNothing().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<MailModel> argument = ArgumentCaptor.forClass(MailModel.class);

        //when
        sut.sentNotificationToReceiver(in);
        verify(threadPoolTaskExecutor).execute(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        //then

        verify(emailService).sendEmail(argument.capture());
        Assertions.assertThat(argument.getValue().getFrom()).isEqualTo(TEST_SEND_EMAIL_ADDRESS);
        Assertions.assertThat(argument.getValue().getMailTo()).isEqualTo(in.getReceiverContactData().getEmailAddress());
        Assertions.assertThat(argument.getValue().getSubject()).contains("confirmation");

        Map<String, Object> props = argument.getValue().getProps();
        Assertions.assertThat(props).containsKeys("information", "name", "parcelId", "showPin", "pin");
        Assertions.assertThat(props.get("name")).isEqualTo(in.getReceiverContactData().getName());
        Assertions.assertThat(props.get("parcelId")).isEqualTo(in.getId());
        Assertions.assertThat(props.get("showPin")).isEqualTo("active");
        Assertions.assertThat(props.get("pin")).isEqualTo(String.valueOf(in.getPin()));
    }

    @Test
    void should_send_return_notification() throws MessagingException {
        //given
        doNothing().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<MailModel> argument = ArgumentCaptor.forClass(MailModel.class);
        //when
        sut.sentReturnNotification(in);
        verify(threadPoolTaskExecutor).execute(argumentCaptor.capture());
        argumentCaptor.getValue().run();
        //then

        verify(emailService).sendEmail(argument.capture());
        Assertions.assertThat(argument.getValue().getFrom()).isEqualTo(TEST_SEND_EMAIL_ADDRESS);
        Assertions.assertThat(argument.getValue().getMailTo()).isEqualTo(in.getSender().getEmailAddress());
        Assertions.assertThat(argument.getValue().getSubject()).contains("return");

        Map<String, Object> props = argument.getValue().getProps();
        Assertions.assertThat(props).containsKeys("information", "name", "parcelId", "showPin", "pin");
        Assertions.assertThat(props.get("name")).isEqualTo(in.getSender().getUserName());
        Assertions.assertThat(props.get("parcelId")).isEqualTo(in.getId());
        Assertions.assertThat(props.get("information").toString()).contains("return");
        Assertions.assertThat(props.get("showPin")).isEqualTo("active");
        Assertions.assertThat(props.get("pin")).isEqualTo(String.valueOf(in.getPin()));
    }

}