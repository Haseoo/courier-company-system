package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static EmployeeView of(EmployeeData employeeData) {
        return EmployeeView
                .builder()
                .id(employeeData.getId())
                .name(employeeData.getName())
                .surname(employeeData.getSurname())
                .pesel(employeeData.getPesel())
                .phoneNumber(employeeData.getPhoneNumber())
                .employeeType(employeeData.getEmployeeType())
                .build();
    }

    public static EmployeeView of(CourierData courierData) {
        return EmployeeView
                .builder()
                .id(courierData.getId())
                .name(courierData.getName())
                .surname(courierData.getSurname())
                .pesel(courierData.getPesel())
                .phoneNumber(courierData.getPesel())
                .employeeType(courierData.getEmployeeType())
                .build();
    }

    public static EmployeeView of(LogisticianData logisticianData) {
        return EmployeeView
                .builder()
                .id(logisticianData.getId())
                .name(logisticianData.getName())
                .surname(logisticianData.getSurname())
                .pesel(logisticianData.getPesel())
                .phoneNumber(logisticianData.getPesel())
                .employeeType(logisticianData.getEmployeeType())
                .build();
    }
}
