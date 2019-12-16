package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.MagazineModel;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository {
    List<MagazineModel> getList();

    Optional<MagazineModel> getById(Long id);

    List<MagazineModel> getActiveMagazines();

    MagazineModel saveAndFlush(MagazineModel magazineModel);
}
