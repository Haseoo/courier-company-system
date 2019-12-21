package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ParcelModel;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
    List<ParcelModel> getList();

    Optional<ParcelModel> getById(Long id);

    ParcelModel saveAndFlush(ParcelModel parcelModel);

    void delete(Long id);
}
