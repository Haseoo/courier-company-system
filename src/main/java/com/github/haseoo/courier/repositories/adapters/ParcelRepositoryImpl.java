package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.repositories.jpa.ParcelJPARepository;
import com.github.haseoo.courier.repositories.ports.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ParcelRepositoryImpl implements ParcelRepository {
    private final ParcelJPARepository parcelJPARepository;

    @Override
    public List<ParcelModel> getList() {
        return parcelJPARepository.findAll();
    }

    @Override
    public Optional<ParcelModel> getById(Long id) {
        return parcelJPARepository.findById(id);
    }

    @Override
    public ParcelModel saveAndFlush(ParcelModel parcelModel) {
        return parcelJPARepository.saveAndFlush(parcelModel);
    }
}
