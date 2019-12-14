package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;

public class ReceiverInfoDataToView extends MapperConverter<ReceiverInfoData, ReceiverInfoView> {
    @Override
    protected ReceiverInfoView convert(ReceiverInfoData receiverInfoData) {
        return ReceiverInfoView
                .builder()
                .emailAddress(receiverInfoData.getEmailAddress())
                .name(receiverInfoData.getName())
                .surname(receiverInfoData.getSurname())
                .phoneNumber(receiverInfoData.getPhoneNumber())
                .id(receiverInfoData.getId())
                .build();
    }
}
