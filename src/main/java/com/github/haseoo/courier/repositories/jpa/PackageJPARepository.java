package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.PackageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageJPARepository extends JpaRepository<PackageModel, Integer> {
}
