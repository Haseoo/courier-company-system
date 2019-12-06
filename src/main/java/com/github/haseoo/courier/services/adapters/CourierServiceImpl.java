package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.services.ports.CourierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CourierData> getList() {
        return courierRepository
                .getList()
                .stream()
                .map(courierModel -> modelMapper.map(courierModel, CourierData.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourierData add(CourierData courierData) {
        if (employeeRepository.findActiveByPesel(courierData.getPesel())
                .stream()
                .anyMatch(employeeModel -> employeeModel instanceof CourierModel)) {
            throw new ActiveCourierExistsException();
        }
        return modelMapper.map(courierRepository
                        .saveAndFlush(modelMapper.map(courierData,
                                CourierModel.class)),
                CourierData.class);
    }

    @Override
    public CourierData getById(Long id) {
        return modelMapper.map(courierRepository
                        .getById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException(id)),
                CourierData.class);
    }

    @Override
    @Transactional
    public CourierData edit(Long id, CourierData newCourierData) {
        return null;
    }
}
