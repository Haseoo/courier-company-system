package com.github.haseoo.courier.servicedata.parcels;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiverInfoData extends ReceiverInfoOperationData {
    private Long id;

    public static ReceiverInfoData of(ReceiverInfoModel receiverInfoModel) {
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
