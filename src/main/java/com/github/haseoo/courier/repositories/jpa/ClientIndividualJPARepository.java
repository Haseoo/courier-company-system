package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ClientIndividualModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientIndividualJPARepository extends JpaRepository<ClientIndividualModel, Long> {
    Optional<ClientIndividualModel> findByPesel(String pesel);
}
