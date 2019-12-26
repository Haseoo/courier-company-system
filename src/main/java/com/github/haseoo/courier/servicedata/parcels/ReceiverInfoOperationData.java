package com.github.haseoo.courier.servicedata.parcels;


import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.models.ReceiverInfoModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static ReceiverInfoOperationData of(ClientIndividualModel clientIndividualModel) {
        return ReceiverInfoOperationData
                .builder()
                .emailAddress(clientIndividualModel.getEmailAddress())
                .name(clientIndividualModel.getName())
                .phoneNumber(clientIndividualModel.getPhoneNumber())
                .surname(clientIndividualModel.getSurname())
                .build();
    }

    public static ReceiverInfoOperationData of(ClientCompanyModel clientCompanyModel) {
        return ReceiverInfoOperationData
                .builder()
                .emailAddress(clientCompanyModel.getEmailAddress())
                .name(clientCompanyModel.getCompanyName())
                .phoneNumber(clientCompanyModel.getPhoneNumber())
                .surname("-")
                .build();
    }
}
