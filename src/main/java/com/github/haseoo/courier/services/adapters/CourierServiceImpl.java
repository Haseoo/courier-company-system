package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.services.ports.CourierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<CourierData> getList() {
        return null;
    }

    @Override
    public CourierData add(CourierData courierData) {
        return modelMapper.map(courierRepository
                        .saveAndFlush(modelMapper.map(courierData,
                                CourierModel.class)),
                CourierData.class);
    }

    @Override
    public CourierData getById(Long id) {
        return null;
    }

    @Override
    public CourierData edit(Long id, CourierData newCourierData) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
