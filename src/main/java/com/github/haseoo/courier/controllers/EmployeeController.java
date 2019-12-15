package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.CourierView;
import com.github.haseoo.courier.views.users.employees.EmployeeView;
import com.github.haseoo.courier.views.users.employees.LogisticianView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
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
}
