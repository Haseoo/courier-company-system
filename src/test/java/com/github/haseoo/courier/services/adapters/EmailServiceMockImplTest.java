package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazineData;
@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class EmailServiceMockImplTest {

    @InjectMocks
    private EmailServiceMockImpl sut;

    @Test
    void email_mock_test() {
        ParcelData parcelData = getParcelInMagazineData();
        sut.sentNotificationToReceiver(parcelData);
        sut.sentNotificationToSender(parcelData);
        sut.sentReturnNotification(parcelData);
    }

}