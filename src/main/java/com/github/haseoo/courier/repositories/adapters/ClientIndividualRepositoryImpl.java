package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClientIndividualRepositoryImpl implements ClientIndividualRepository {
    private final ClientIndividualJPARepository clientIndividualJPARepository;

    @Override
    public ClientIndividualModel saveAndFlush(ClientIndividualModel clientCompanyModel) {
        return clientIndividualJPARepository.saveAndFlush(clientCompanyModel);
    }

    @Override
    public Optional<ClientIndividualModel> getById(Long id) {
        return clientIndividualJPARepository.findById(id);
    }

    @Override
    public Optional<ClientIndividualModel> getByPesel(String pesel) {
        return clientIndividualJPARepository.findByPesel(pesel);
    }

    @Override
    public ClientIndividualModel getByEmailAddress(String email) {
        return clientIndividualJPARepository.findByEmailAddress(email);
    }
}
