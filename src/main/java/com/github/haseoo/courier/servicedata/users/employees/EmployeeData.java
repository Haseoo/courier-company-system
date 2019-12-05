package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.models.EmployeeModel;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.*;

import static com.github.haseoo.courier.utilities.UserUtils.getEmployeeType;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class EmployeeData {
    private Long id;
    private String userName;
    private char[] password;
    private Boolean active;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;
    private EmployeeType employeeType;

    public static EmployeeData of(EmployeeModel employeeModel) {
        return EmployeeData.builder()
                .id(employeeModel.getId())
                .userName(employeeModel.getUserName())
                .password(employeeModel.getPassword())
                .active(employeeModel.getActive())
                .name(employeeModel.getName())
                .surname(employeeModel.getSurname())
                .phoneNumber(employeeModel.getPhoneNumber())
                .pesel(employeeModel.getPesel())
                .employeeType(UserUtils.getEmployeeType(employeeModel))
                .build();
    }
}
