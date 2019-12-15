package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.enums.EmployeeType;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class EmployeeView {
    private Long id;
    private EmployeeType employeeType;
    private String pesel;
    private String name;
    private String surname;
    private String phoneNumber;
}
