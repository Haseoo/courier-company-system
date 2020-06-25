package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;

import static com.github.haseoo.courier.testutlis.constants.Constants.TEST_SEND_EMAIL_ADDRESS;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelAtSender;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailServiceProdImplTest {
    @Mock
    private EmailSenderServiceImpl emailService;

    @Mock
    private EmailNotificationServiceImpl emailNotificationService;

    @Spy
    private ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    @InjectMocks
    private EmailServiceProdImpl sut;

    @BeforeEach
    void beforeEach() {
        threadPoolTaskExecutor.initialize();
        ReflectionTestUtils.setField(sut,
                EmailServiceProdImpl.class,
                "mailFrom",
                TEST_SEND_EMAIL_ADDRESS,
                String.class);
    }


    @Test
    void should_send_notification_to_sender() throws MessagingException {
        //given
        doCallRealMethod().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        //when
        sut.sentNotificationToSender(in);
        //then
        verify(threadPoolTaskExecutor).execute(any());
    }

    @Test
    void should_send_notification_to_receiver() throws MessagingException, InterruptedException {
        //given
        doCallRealMethod().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        //when
        sut.sentNotificationToReceiver(in);
        //then
        verify(threadPoolTaskExecutor).execute(any());
    }

    @Test
    void should_send_return_notification() throws MessagingException {
        //given
        doCallRealMethod().when(threadPoolTaskExecutor).execute(any());
        ParcelData in = ParcelData.of(getParcelAtSender());
        //when
        sut.sentReturnNotification(in);
        //then
        verify(threadPoolTaskExecutor).execute(any());
    }

}