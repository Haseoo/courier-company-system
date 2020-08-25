package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ParcelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelJPARepository extends JpaRepository<ParcelModel, Long> {
}
