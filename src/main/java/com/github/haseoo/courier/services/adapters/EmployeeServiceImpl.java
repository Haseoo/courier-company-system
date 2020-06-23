package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    private CourierService courierService;

    @Autowired
    private LogisticianService logisticianService;

    @Override
    public List<EmployeeData> getList() {
        return employeeRepository
                .getList()
                .stream()
                .map(EmployeeData::of)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeData getById(Long id) {
        return EmployeeData.of(employeeRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
    }

    @Override
    public EmployeeData addEmployee(EmployeeAddCommandData employeeAddOperationData) {
        if (employeeAddOperationData.getEmployeeType() == EmployeeType.COURIER) {
            return courierService.add(EmployeeAddOperationData.of(employeeAddOperationData));
        }
        if (employeeAddOperationData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return logisticianService.add(EmployeeAddOperationData.of(employeeAddOperationData));
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }

    @Override
    public EmployeeData editEmployee(Long id, EmployeeEditCommandData employeeEditOperationData) {
        if (employeeEditOperationData.getEmployeeType() == EmployeeType.COURIER) {
            return courierService.edit(id, EmployeeEditOperationData.of(employeeEditOperationData));
        }
        if (employeeEditOperationData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return logisticianService.edit(id, EmployeeEditOperationData.of(employeeEditOperationData));
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }
}
