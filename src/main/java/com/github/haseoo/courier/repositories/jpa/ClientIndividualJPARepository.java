package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ClientIndividualModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientIndividualJPARepository extends CrudRepository<ClientIndividualModel, Long> {
    ClientIndividualModel saveAndFlush(ClientIndividualModel clientCompanyModel);
    Optional<ClientIndividualModel> findByPesel(String pesel);
    ClientIndividualModel findByEmailAddress(String email);
}
