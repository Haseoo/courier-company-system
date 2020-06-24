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
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import java.util.Map;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazineData;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class EmailNotificationServiceImplTest {
    private static final String TEST_SEND_EMAIL_ADDRESS = "test@test.ts";
    @Mock
    private EmailSenderServiceImpl emailSenderService;
    @InjectMocks
    private EmailNotificationServiceImpl sut;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(sut,
                EmailNotificationServiceImpl.class,
                "mailFrom",
                TEST_SEND_EMAIL_ADDRESS,
                String.class);

    }

    @Test
    void should_send_notification_to_receiver() throws MessagingException {
        //given
        ParcelData in = getParcelInMagazineData();
        final String information = "Test information";
        ArgumentCaptor<MailModel> argument = ArgumentCaptor.forClass(MailModel.class);
        //when
        sut.sendNotificationToReceiver(in, information);
        verify(emailSenderService).sendEmail(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getFrom()).isEqualTo(TEST_SEND_EMAIL_ADDRESS);
        Assertions.assertThat(argument.getValue().getMailTo()).isEqualTo(in.getReceiverContactData().getEmailAddress());
        Assertions.assertThat(argument.getValue().getSubject()).contains("JanuszeX");

        Map<String, Object> props = argument.getValue().getProps();
        Assertions.assertThat(props).containsKeys("information", "name", "parcelId", "showPin");
        Assertions.assertThat(props.get("information")).isEqualTo(information);
        Assertions.assertThat(props.get("name")).isEqualTo(in.getReceiverContactData().getName());
        Assertions.assertThat(props.get("parcelId")).isEqualTo(in.getId());
        Assertions.assertThat(props.get("showPin")).isEqualTo("inactive");
    }
}