package com.github.haseoo.courier.servicedata.users.employees;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
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
}
