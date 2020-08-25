package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.EmployeeType.LOGISTICIAN;
import static com.github.haseoo.courier.utilities.UserUtils.validatePesel;
import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class LogisticianServiceImpl implements LogisticianService {
    private final EmployeeRepository employeeRepository;
    private final LogisticianRepository logisticianRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<LogisticianData> getList() {
        return logisticianRepository
                .getList()
                .stream()
                .map(LogisticianData::of)
                .collect(Collectors.toList());
    }

    @Override
    public void consumeAllById(List<Long> ids, Consumer<List<LogisticianModel>> consumer) {
        consumer.accept(ids
                .stream()
                .map(id -> logisticianRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id, LOGISTICIAN)))
                .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public LogisticianData add(EmployeeAddOperationData addOperationData) {
        validatePesel(addOperationData.getPesel());
        validatePeselExistence(addOperationData.getPesel());
        LogisticianModel modelToAdd = modelMapper.map(addOperationData, LogisticianModel.class);
        modelToAdd.setPassword(passwordEncoder.encode(String.valueOf(addOperationData.getPassword())).toCharArray());
        userService.checkUsername(addOperationData.getUserName());
        return LogisticianData.of(logisticianRepository
                .saveAndFlush(modelToAdd));
    }

    @Override
    public LogisticianData getById(Long id) {
        return LogisticianData.of(logisticianRepository
                .getById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id, LOGISTICIAN)));
    }

    @Override
    @Transactional
    public LogisticianData edit(Long id, EmployeeEditOperationData editOperationData) {
        LogisticianModel logisticianModel = logisticianRepository.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id, LOGISTICIAN));
        if (peselChanged(editOperationData, logisticianModel)) {
            validatePeselExistence(editOperationData.getPesel());
        }
        CourierModel modelToEdit = modelMapper.map(editOperationData, CourierModel.class);
        if (editOperationData.getPassword() != null) {
            modelToEdit.setPassword(passwordEncoder.encode(String.valueOf(editOperationData.getPassword())).toCharArray());
        }
        copyNonNullProperties(modelToEdit, logisticianModel);
        return LogisticianData.of(logisticianRepository.saveAndFlush(logisticianModel));
    }

    private void validatePeselExistence(String pesel) {
        if (employeeRepository.findActiveByPesel(pesel)
                .stream()
                .anyMatch(employeeModel -> employeeModel instanceof LogisticianModel)) {
            throw new ActiveCourierExistsException();
        }
    }

    private boolean peselChanged(EmployeeEditOperationData operationData, LogisticianModel logisticianModel) {
        return operationData.getPesel() != null && !operationData.getPesel().equals(logisticianModel.getPesel());
    }
}
