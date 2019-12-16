package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;

import java.util.List;
import java.util.function.Consumer;

public interface LogisticianService {
    List<LogisticianData> getList();

    void consumeAllById(List<Long> ids, Consumer<List<LogisticianModel>> consumer);

    LogisticianData add(EmployeeAddOperationData logisticianData);

    LogisticianData getById(Long id);

    LogisticianData edit(Long id, EmployeeEditOperationData data);
}
