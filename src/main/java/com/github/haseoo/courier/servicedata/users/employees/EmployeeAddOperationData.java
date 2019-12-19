package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class EmployeeAddOperationData {
    @NonNull
    private String userName;
    @NonNull
    private char[] password;
    @NonNull
    private Boolean active;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String pesel;

    public static EmployeeAddOperationData of(EmployeeAddCommandData commandData) {
        return EmployeeAddOperationData
                .builder()
                .userName(commandData.getUserName())
                .password(commandData.getPassword())
                .active(true)
                .name(commandData.getName())
                .surname(commandData.getSurname())
                .phoneNumber(commandData.getPhoneNumber())
                .pesel(commandData.getPesel())
                .build();
    }
}
