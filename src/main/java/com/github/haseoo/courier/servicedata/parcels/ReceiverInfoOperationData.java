package com.github.haseoo.courier.servicedata.parcels;


import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
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

    public static ReceiverInfoOperationData of(ReceiverInfoCommandData commandData){
        return new ReceiverInfoOperationData(
                commandData.getName(),
                commandData.getSurname(),
                commandData.getEmailAddress(),
                commandData.getPhoneNumber()
        );
    }
}
//ADd parcel
