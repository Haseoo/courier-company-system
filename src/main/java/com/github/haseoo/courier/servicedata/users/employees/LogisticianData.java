package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class LogisticianData{
    private Long id;
    private String userName;
    private char[] password;
    private Boolean active;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
    private EmployeeType employeeType;
    //private MagazineData magazine;
}
