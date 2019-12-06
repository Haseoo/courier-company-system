package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.employees.ActiveLogisticianExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.services.ports.LogisticianService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticianServiceImpl implements LogisticianService {
    private final EmployeeRepository employeeRepository;
    private final LogisticianRepository logisticianRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LogisticianData> getList() {
        return logisticianRepository
                .getList()
                .stream()
                .map(logisticianModel -> modelMapper.map(logisticianModel, LogisticianData.class))
                .collect(Collectors.toList());
    }

    @Override
    public LogisticianData add(LogisticianData logisticianData) {
        if (employeeRepository.findActiveByPesel(logisticianData.getPesel())
                .stream()
                .anyMatch(employeeModel -> employeeModel instanceof LogisticianModel)) {
            throw new ActiveLogisticianExistsException();
        }
        return modelMapper.map(logisticianRepository
                        .saveAndFlush(modelMapper.map(logisticianData,
                                LogisticianModel.class)),
                LogisticianData.class);
    }

    @Override
    public LogisticianData getById(Long id) {
        return modelMapper.map(logisticianRepository
                        .getById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException(id)),
                LogisticianData.class);
    }

    @Override
    public LogisticianData edit(Long id, LogisticianData newLogisticianData) {
        return null;
    }
}
