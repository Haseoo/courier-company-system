package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;

import java.util.List;

public interface CourierService {
    List<CourierData> getList();
    CourierData add(CourierData courierData);
    CourierData getById(Long id);
    CourierData edit(Long id, CourierData newCourierData);
    void remove(Long id);
}
