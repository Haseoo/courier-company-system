package com.github.haseoo.courier.views.receiver.info;


import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
public class ReceiverInfoView {
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
    private String phoneNumber;

    public static ReceiverInfoView of(ReceiverInfoData receiverInfoData) {
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
