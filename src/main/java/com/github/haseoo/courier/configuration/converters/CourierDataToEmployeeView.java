package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.views.users.employees.EmployeeView;

public class CourierDataToEmployeeView extends MapperConverter<CourierData, EmployeeView> {
    @Override
    protected EmployeeView convert(CourierData courierData) {
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
}
