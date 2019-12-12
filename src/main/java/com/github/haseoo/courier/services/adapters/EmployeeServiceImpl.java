package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.services.ports.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeData> getList() {
        return employeeRepository
                .getList()
                .stream()
                .map(EmployeeData::of)
                .collect(Collectors.toList());
    }
}
