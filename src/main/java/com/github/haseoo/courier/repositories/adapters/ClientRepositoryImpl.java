package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientModel;
import com.github.haseoo.courier.repositories.jpa.ClientJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientJPARepository clientJPARepository;

    @Override
    public List<ClientModel> getList() {
        return clientJPARepository.findAll();
    }

    @Override
    public Optional<ClientModel> getById(Long id) {
        return clientJPARepository.findById(id);
    }
}
