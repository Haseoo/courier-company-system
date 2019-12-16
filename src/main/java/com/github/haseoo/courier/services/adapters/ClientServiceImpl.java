package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.repositories.ports.ClientRepository;
import com.github.haseoo.courier.servicedata.users.clients.ClientData;
import com.github.haseoo.courier.services.ports.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public List<ClientData> getList() {
        return clientRepository.getList()
                .stream()
                .map(ClientData::of)
                .collect(Collectors.toList());
    }
}
