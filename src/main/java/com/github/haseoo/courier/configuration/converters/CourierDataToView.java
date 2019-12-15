package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.views.users.employees.CourierView;

public class CourierDataToView extends MapperConverter<CourierData, CourierView> {
    @Override
    protected CourierView convert(CourierData courierData) {
        return CourierView
                .builder()
                .id(courierData.getId())
                .name(courierData.getName())
                .surname(courierData.getSurname())
                .pesel(courierData.getPesel())
                .phoneNumber(courierData.getPhoneNumber())
                .build();
    }
}
