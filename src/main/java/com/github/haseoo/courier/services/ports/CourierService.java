package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.CourierOperationData;

import java.util.List;

public interface CourierService {
    List<CourierData> getList();

    CourierData add(CourierOperationData courierOperationData);

    CourierData getById(Long id);

    CourierData edit(Long id, CourierOperationData newCourierOperationData);
}
