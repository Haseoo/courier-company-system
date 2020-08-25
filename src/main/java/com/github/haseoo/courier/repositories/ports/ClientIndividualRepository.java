package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ClientIndividualModel;

import java.util.Optional;

public interface ClientIndividualRepository {
    ClientIndividualModel saveAndFlush(ClientIndividualModel clientCompanyModel);

    Optional<ClientIndividualModel> getById(Long id);

    Optional<ClientIndividualModel> getByPesel(String pesel);

    ClientIndividualModel getByEmailAddress(String email);
}
