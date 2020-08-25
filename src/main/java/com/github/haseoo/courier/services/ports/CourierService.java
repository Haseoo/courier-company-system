package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;

import java.util.List;

public interface CourierService {
    List<CourierData> getList();

    CourierData add(EmployeeAddOperationData courierOperationData);

    CourierData getById(Long id);

    CourierData edit(Long id, EmployeeEditOperationData courierEditOperationData);
}
