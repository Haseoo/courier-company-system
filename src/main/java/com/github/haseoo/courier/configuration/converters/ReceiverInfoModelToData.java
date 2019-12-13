package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;

public class ReceiverInfoModelToData extends MapperConverter<ReceiverInfoModel, ReceiverInfoData> {
    @Override
    protected ReceiverInfoData convert(ReceiverInfoModel receiverInfoModel) {
        return ReceiverInfoData
                .builder()
                .id(receiverInfoModel.getId())
                .emailAddress(receiverInfoModel.getEmailAddress())
                .name(receiverInfoModel.getName())
                .phoneNumber(receiverInfoModel.getPhoneNumber())
                .surname(receiverInfoModel.getSurname())
                .build();
    }
}
