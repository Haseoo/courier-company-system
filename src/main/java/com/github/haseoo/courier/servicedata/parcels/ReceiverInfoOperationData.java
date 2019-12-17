package com.github.haseoo.courier.servicedata.parcels;


import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
import com.github.haseoo.courier.models.ReceiverInfoModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiverInfoOperationData {
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String emailAddress;
    @NonNull
    private String phoneNumber;

    public static ReceiverInfoOperationData of(ReceiverInfoCommandData commandData) {
        return new ReceiverInfoOperationData(
                commandData.getName(),
                commandData.getSurname(),
                commandData.getEmailAddress(),
                commandData.getPhoneNumber()
        );
    }

    public static ReceiverInfoOperationData of(ReceiverInfoModel receiverInfoModel) {
        return ReceiverInfoOperationData
                .builder()
                    .emailAddress(receiverInfoModel.getEmailAddress())
                    .name(receiverInfoModel.getName())
                    .phoneNumber(receiverInfoModel.getPhoneNumber())
                    .surname(receiverInfoModel.getSurname())
                .build();
    }
}
//ADd parcel
