package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.CourierOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.UserUtils.validatePesel;

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
    public CourierData add(CourierOperationData courierOperationData) {
        validatePesel(courierOperationData.getPesel());
        if (employeeRepository.findActiveByPesel(courierOperationData.getPesel())
                .stream()
                .anyMatch(employeeModel -> employeeModel instanceof CourierModel)) {
            throw new ActiveCourierExistsException();
        }
        return modelMapper.map(courierRepository
                        .saveAndFlush(modelMapper.map(courierOperationData,
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
    public CourierData edit(Long id, CourierOperationData courierOperationData) {
        CourierModel old = courierRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        CourierModel in = modelMapper.map(courierOperationData, CourierModel.class);
        in.setId(old.getId());
        return modelMapper.map(courierRepository.saveAndFlush(in), CourierData.class);
    }
}
