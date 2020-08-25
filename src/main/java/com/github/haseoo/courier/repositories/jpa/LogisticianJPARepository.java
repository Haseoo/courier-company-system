package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.LogisticianModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogisticianJPARepository extends JpaRepository<LogisticianModel, Long> {
    List<LogisticianModel> findAllByActiveTrue();
}
