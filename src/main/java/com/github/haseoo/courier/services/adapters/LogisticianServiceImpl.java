package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.services.ports.LogisticianService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogisticianServiceImpl implements LogisticianService {
    private final LogisticianRepository logisticianRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<LogisticianData> getList() {
        return null;
    }

    @Override
    public LogisticianData add(LogisticianData logisticianData) {
        return modelMapper.map(logisticianRepository
                .saveAndFlush(modelMapper.map(logisticianData,
                        LogisticianModel.class)),
                LogisticianData.class);
    }

    @Override
    public LogisticianData getById(Long id) {
        return null;
    }

    @Override
    public LogisticianData edit(Long id, LogisticianData newLogisticianData) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
