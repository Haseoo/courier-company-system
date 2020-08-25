package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<CourierData> getList() {
        return courierRepository
                .getList()
                .stream()
                .map(CourierData::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourierData add(EmployeeAddOperationData addOperationData) {
        validatePesel(addOperationData.getPesel());
        validatePeselExistence(addOperationData.getPesel());
        userService.checkUsername(addOperationData.getUserName());
        CourierModel modelToAdd = modelMapper.map(addOperationData, CourierModel.class);
        modelToAdd.setPassword(passwordEncoder.encode(String.valueOf(addOperationData.getPassword())).toCharArray());
        return CourierData.of(courierRepository.saveAndFlush(modelToAdd));
    }

    @Override
    public CourierData getById(Long id) {
        return CourierData.of(courierRepository
                .getById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id, EmployeeType.COURIER)));
    }

    @Override
    @Transactional
    public CourierData edit(Long id, EmployeeEditOperationData editOperationData) {
        CourierModel courierModel = courierRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id, EmployeeType.COURIER));
        CourierModel modelToUpdate = modelMapper.map(editOperationData, CourierModel.class);
        if (peselChanged(editOperationData, courierModel)) {
            validatePeselExistence(editOperationData.getPesel());
        }
        if (editOperationData.getPassword() != null) {
            modelToUpdate.setPassword(passwordEncoder.encode(String.valueOf(editOperationData.getPassword())).toCharArray());
        }
        copyNonNullProperties(modelToUpdate, courierModel);
        return CourierData.of(courierRepository.saveAndFlush(courierModel));
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
