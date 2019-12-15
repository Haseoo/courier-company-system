package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.views.users.employees.EmployeeView;

public class LogisticianDataToEmployeeView extends MapperConverter<LogisticianData, EmployeeView> {
    @Override
    protected EmployeeView convert(LogisticianData logisticianData) {
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
