package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.MagazineModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MagazineJPARepository extends JpaRepository<MagazineModel, Long> {
    List<MagazineModel> findAllByActiveTrue();
}
