package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ClientModel;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<ClientModel> getList();

    Optional<ClientModel> getById(Long id);
}
