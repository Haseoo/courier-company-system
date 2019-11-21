package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ParcelTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelTypeJPARepository extends JpaRepository<ParcelTypeModel, Long> {
    List<ParcelTypeModel> getAllByActiveTrue();
}
