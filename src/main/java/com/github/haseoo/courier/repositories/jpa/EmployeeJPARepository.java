package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJPARepository extends JpaRepository<EmployeeModel, Long> {
    List<EmployeeModel> findByPeselAndActiveTrue(String pesel);
}
