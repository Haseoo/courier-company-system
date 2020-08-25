package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ClientCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientCompanyJPARepository extends JpaRepository<ClientCompanyModel, Long> {
    Optional<ClientCompanyModel> findByNip(String nip);
}
