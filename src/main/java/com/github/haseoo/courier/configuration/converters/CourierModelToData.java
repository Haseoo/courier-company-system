package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;

import static com.github.haseoo.courier.utilities.UserUtils.getEmployeeType;
import static com.github.haseoo.courier.utilities.UserUtils.getUserType;


public class CourierModelToData extends MapperConverter<CourierModel, CourierData> {

    @Override
    protected CourierData convert(CourierModel courierModel) {
        return CourierData.builder()
                .id(courierModel.getId())
                .userName(courierModel.getUserName())
                .password(courierModel.getPassword())
                .active(courierModel.getActive())
                .name(courierModel.getName())
                .surname(courierModel.getSurname())
                .phoneNumber(courierModel.getPhoneNumber())
                .pesel(courierModel.getPesel())
                .employeeType(getEmployeeType(courierModel))
                .userType(getUserType(courierModel))
                .build();
    }
}
