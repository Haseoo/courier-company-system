package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.CourierModel;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {
    List<CourierModel> getList();

    Optional<CourierModel> getById(Long id);

    CourierModel saveAndFlush(CourierModel courierModel);
}
