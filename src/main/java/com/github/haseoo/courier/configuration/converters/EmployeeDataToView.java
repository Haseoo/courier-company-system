package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.views.users.employees.EmployeeView;

public class EmployeeDataToView extends MapperConverter<EmployeeData, EmployeeView> {
    @Override
    protected EmployeeView convert(EmployeeData employeeData) {
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
}
