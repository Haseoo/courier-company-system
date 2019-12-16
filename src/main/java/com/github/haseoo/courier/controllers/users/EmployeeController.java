package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CourierService courierService;
    private final LogisticianService logisticianService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<EmployeeView> getList() {
        return employeeService.getList()
                .stream()
                .map(employeeData -> modelMapper.map(employeeData, EmployeeView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public EmployeeView getById(@PathVariable Long id) {
        return modelMapper.map(employeeService.getById(id), EmployeeView.class);
    }

    @PutMapping
    public EmployeeView add(@RequestBody EmployeeAddCommandData addCommandData) {
        if (addCommandData.getEmployeeType() == EmployeeType.COURIER) {
            return modelMapper.map(courierService.add(EmployeeAddOperationData.of(addCommandData)), EmployeeView.class);
        }
        if (addCommandData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return modelMapper.map(logisticianService.add(EmployeeAddOperationData.of(addCommandData)), EmployeeView.class);
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }

    @PostMapping("{id}")
    public EmployeeView add(@PathVariable Long id, @RequestBody EmployeeEditCommandData editOperationData) {
        if (editOperationData.getEmployeeType() == EmployeeType.COURIER) {
            return modelMapper.map(courierService.edit(id, EmployeeEditOperationData.of(editOperationData)), EmployeeView.class);
        }
        if (editOperationData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return modelMapper.map(logisticianService.edit(id, EmployeeEditOperationData.of(editOperationData)), EmployeeView.class);
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }
}
