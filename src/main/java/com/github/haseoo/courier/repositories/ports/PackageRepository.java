package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.PackageModel;

import java.util.List;
import java.util.Optional;

public interface PackageRepository {
    List<PackageModel> getList();
    Optional<PackageModel> getById(Integer id);
    PackageModel saveAndFlush(PackageModel packageModelObj);
}
