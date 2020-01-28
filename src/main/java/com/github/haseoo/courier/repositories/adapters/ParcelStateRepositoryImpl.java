package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.repositories.jpa.ParcelStateJPARepository;
import com.github.haseoo.courier.repositories.ports.ParcelStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParcelStateRepositoryImpl implements ParcelStateRepository {
    private final ParcelStateJPARepository parcelStateRepository;
    @Override
    public void deleteById(Long id) {
        parcelStateRepository.deleteAndFlushById(id);
    }
}
