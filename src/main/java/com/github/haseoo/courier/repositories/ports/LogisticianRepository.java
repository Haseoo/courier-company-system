package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.LogisticianModel;

import java.util.List;
import java.util.Optional;

public interface LogisticianRepository {
    List<LogisticianModel> getList();

    Optional<LogisticianModel> getById(Long id);

    LogisticianModel saveAndFlush(LogisticianModel logisticianModel);
}
