package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.jpa.CourierJPARepository;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CourierRepositoryImpl implements CourierRepository {
    private final CourierJPARepository courierJPARepository;

    @Override
    public List<CourierModel> getList() {
        return courierJPARepository.findAll();
    }

    @Override
    public Optional<CourierModel> getById(Long id) {
        return courierJPARepository.findById(id);
    }

    @Override
    public CourierModel saveAndFlush(CourierModel courierModel) {
        return courierJPARepository.saveAndFlush(courierModel);
    }
}
