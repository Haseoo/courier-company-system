package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJPARepository extends JpaRepository<EmployeeModel, Long> {
}
