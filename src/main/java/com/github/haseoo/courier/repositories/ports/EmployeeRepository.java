package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.EmployeeModel;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeModel> getList();
}
