package com.github.haseoo.courier.querydata;


import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class ReceiverInfoQueryData {
    private String name;
    private String surname;
    private String emailAddress;
    private String phoneNumber;

    public static ReceiverInfoQueryData of(ReceiverInfoOperationData receiverInfo){
        return ReceiverInfoQueryData
                .builder()
                    .emailAddress(receiverInfo.getEmailAddress())
                    .name(receiverInfo.getName())
                    .surname(receiverInfo.getSurname())
                    .phoneNumber(receiverInfo.getPhoneNumber())
                .build();
    }
}
