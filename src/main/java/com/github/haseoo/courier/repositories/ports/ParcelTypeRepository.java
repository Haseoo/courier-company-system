package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ParcelTypeModel;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeRepository {
    ParcelTypeModel saveAndFlush(ParcelTypeModel parcelTypeModel);

    List<ParcelTypeModel> getList();

    Optional<ParcelTypeModel> getById(Long id);

    List<ParcelTypeModel> getActiveTypes();

    void delete(Long id);
}
