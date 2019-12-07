package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.servicedata.users.employees.CourierOperationData;


public class CourierModelToOperationData extends MapperConverter<CourierModel, CourierOperationData> {

    @Override
    protected CourierOperationData convert(CourierModel courierModel) {
        return CourierOperationData.builder()
                .userName(courierModel.getUserName())
                .password(courierModel.getPassword())
                .active(courierModel.getActive())
                .name(courierModel.getName())
                .surname(courierModel.getSurname())
                .phoneNumber(courierModel.getPhoneNumber())
                .pesel(courierModel.getPesel())
                .build();
    }
}
