package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;

import java.util.List;

public interface EmployeeService {
    List<EmployeeData> getList();
    EmployeeData getById(Long id);
}
