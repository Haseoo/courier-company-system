package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.MagazineModel;

import java.util.List;

public interface MagazineRepository {
    List<MagazineModel> getList();

    List<MagazineModel> getActiveMagazines();

    MagazineModel saveAndFlush(MagazineModel magazineModel);
}
