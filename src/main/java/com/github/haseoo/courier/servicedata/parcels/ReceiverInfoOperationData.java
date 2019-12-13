package com.github.haseoo.courier.servicedata.parcels;


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
}
//ADd parcel
