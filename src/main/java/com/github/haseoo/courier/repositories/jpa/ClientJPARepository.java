package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJPARepository extends JpaRepository<ClientModel, Long> {
}
