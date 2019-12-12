package com.github.haseoo.courier.servicedata.users.employees;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class CourierOperationData {
    private String userName;
    private char[] password;
    private Boolean active;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
}
