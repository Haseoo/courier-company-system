package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.repositories.jpa.ClientCompanyJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClientCompanyRepositoryImpl implements ClientCompanyRepository {
    private final ClientCompanyJPARepository clientCompanyJPARepository;

    @Override
    public ClientCompanyModel saveAndFlush(ClientCompanyModel clientCompanyModel) {
        return clientCompanyJPARepository.saveAndFlush(clientCompanyModel);
    }

    @Override
    public Optional<ClientCompanyModel> getById(Long id) {
        return clientCompanyJPARepository.findById(id);
    }

    @Override
    public Optional<ClientCompanyModel> getByNip(String nip) {
        return clientCompanyJPARepository.findByNip(nip);
    }
}
