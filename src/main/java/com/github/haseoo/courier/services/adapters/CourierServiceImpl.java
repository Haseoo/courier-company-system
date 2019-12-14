package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.UserUtils.validatePesel;
import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

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
    public CourierData add(EmployeeAddOperationData addOperationData) {
        validatePesel(addOperationData.getPesel());
        validatePeselExistence(addOperationData.getPesel());
        return modelMapper.map(courierRepository
                        .saveAndFlush(modelMapper.map(addOperationData,
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
    public CourierData edit(Long id, EmployeeEditOperationData editOperationData) {
        CourierModel courierModel = courierRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        if (peselChanged(editOperationData, courierModel)) {
            validatePeselExistence(editOperationData.getPesel());
        }
        copyNonNullProperties(modelMapper.map(editOperationData, CourierModel.class), courierModel);
        return modelMapper.map(courierRepository.saveAndFlush(courierModel), CourierData.class);
    }

    private void validatePeselExistence(String pesel) {
        if (employeeRepository.findActiveByPesel(pesel)
                .stream()
                .anyMatch(employeeModel -> employeeModel instanceof CourierModel)) {
            throw new ActiveCourierExistsException();
        }
    }

    private boolean peselChanged(EmployeeEditOperationData courierEditOperationData, CourierModel courierModel) {
        return courierEditOperationData.getPesel() != null && !courierEditOperationData.getPesel().equals(courierModel.getPesel());
    }
}
