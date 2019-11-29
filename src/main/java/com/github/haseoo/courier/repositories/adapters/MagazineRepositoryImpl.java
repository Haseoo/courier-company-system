package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.repositories.jpa.MagazineJPARepository;
import com.github.haseoo.courier.repositories.ports.MagazineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MagazineRepositoryImpl implements MagazineRepository {
    private final MagazineJPARepository magazineJPARepository;

    @Override
    public List<MagazineModel> getList() {
        return magazineJPARepository.findAll();
    }

    @Override
    public List<MagazineModel> getActiveMagazines() {
        return magazineJPARepository.findAllByActiveTrue();
    }

    @Override
    public MagazineModel saveAndFlush(MagazineModel magazineModel) {
        return magazineJPARepository.saveAndFlush(magazineModel);
    }
}
