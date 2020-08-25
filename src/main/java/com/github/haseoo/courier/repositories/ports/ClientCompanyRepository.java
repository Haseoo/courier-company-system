package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ClientCompanyModel;

import java.util.Optional;

public interface ClientCompanyRepository {
    ClientCompanyModel saveAndFlush(ClientCompanyModel clientCompanyModel);

    Optional<ClientCompanyModel> getById(Long id);

    Optional<ClientCompanyModel> getByNip(String nip);
}
