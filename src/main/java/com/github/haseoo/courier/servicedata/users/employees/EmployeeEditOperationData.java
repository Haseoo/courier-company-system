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
public class EmployeeEditOperationData {
    private char[] password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
}
