package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.CourierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierJPARepository extends JpaRepository<CourierModel, Long> {
    List<CourierModel> findAllByActiveTrue();
}
