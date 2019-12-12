package com.github.haseoo.courier.servicedata.users.employees;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class CourierData {
    private Long id;
    private String userName;
    private char[] password;
    private Boolean active;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
    /*private List<ParcelData> assignedParcels*/
}
