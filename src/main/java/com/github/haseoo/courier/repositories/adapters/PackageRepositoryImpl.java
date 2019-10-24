package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.PackageModel;
import com.github.haseoo.courier.repositories.jpa.PackageJPARepository;
import com.github.haseoo.courier.repositories.ports.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PackageRepositoryImpl implements PackageRepository {
    private final PackageJPARepository packageJPARepository;
    @Override
    public List<PackageModel> getList() {
        return packageJPARepository.findAll();
    }

    @Override
    public Optional<PackageModel> getById(Integer id) {
        return packageJPARepository.findById(id);
    }

    @Override
    public PackageModel saveAndFlush(PackageModel packageModelObj) {
        return packageJPARepository.saveAndFlush(packageModelObj);
    }
}
