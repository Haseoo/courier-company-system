package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;

import java.util.List;

public interface LogisticianService {
    List<LogisticianData> getList();

    LogisticianData add(EmployeeAddOperationData logisticianData);

    LogisticianData getById(Long id);

    LogisticianData edit(Long id, EmployeeEditOperationData data);
}
