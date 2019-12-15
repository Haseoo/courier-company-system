package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
//@NoArgsConstructor
//@AllArgsConstructor(access = PRIVATE)
public class EmployeeEditOperationData {
    private char[] password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;

    public static EmployeeEditOperationData of(EmployeeEditCommandData commandData) {
        return EmployeeEditOperationData
                .builder()
                    .password(commandData.getPassword())
                    .name(commandData.getName())
                    .surname(commandData.getSurname())
                    .phoneNumber(commandData.getPhoneNumber())
                    .pesel(commandData.getPesel())
                .build();
    }
}
