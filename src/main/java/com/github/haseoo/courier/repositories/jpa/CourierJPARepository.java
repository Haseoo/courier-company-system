package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.CourierModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierJPARepository extends JpaRepository<CourierModel, Long> {
}
