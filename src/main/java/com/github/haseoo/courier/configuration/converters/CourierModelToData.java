package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;


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
                .build();
    }
}
