package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;

public class ReceiverInfoModelToOperationData extends MapperConverter<ReceiverInfoModel, ReceiverInfoOperationData> {
    @Override
    protected ReceiverInfoOperationData convert(ReceiverInfoModel receiverInfoModel) {
        return ReceiverInfoOperationData
                .builder()
                .emailAddress(receiverInfoModel.getEmailAddress())
                .name(receiverInfoModel.getName())
                .phoneNumber(receiverInfoModel.getPhoneNumber())
                .surname(receiverInfoModel.getSurname())
                .build();
    }
}
