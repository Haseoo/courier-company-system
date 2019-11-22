package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ClientCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCompanyJPARepository extends JpaRepository<ClientCompanyModel, Long> {
}
