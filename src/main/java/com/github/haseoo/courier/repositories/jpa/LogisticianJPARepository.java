package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.LogisticianModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticianJPARepository extends JpaRepository<LogisticianModel, Long> {
}
