package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.EmployeeModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<EmployeeModel> getList();

    Optional<EmployeeModel> getById(Long id);

    List<EmployeeModel> findActiveByPesel(String pesel);
}
