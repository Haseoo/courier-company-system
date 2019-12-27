package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.exceptions.ResourceException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.repositories.ports.ClientRepository;
import com.github.haseoo.courier.security.UserDetailsImpl;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public List<ClientData> getList() {
        return clientRepository.getList()
                .stream()
                .map(ClientData::of)
                .collect(Collectors.toList());
    }

    @Override
    public ClientData getById(Long id) {
        UserDetailsImpl userDetails = userDetailsService.currentUser();
        verifyResource(id, userDetails);
        return ClientData.of(clientRepository.getById(id).orElseThrow(() -> new ClientNotFound(id)));
    }

    private void verifyResource(Long id, UserDetailsImpl userDetails) {
        if (isUserClient(userDetails) && !userDetails.getId().equals(id)) {
            throw new ResourceException();
        }
    }

    private boolean isUserClient(UserDetailsImpl userDetails) {
        return userDetails.getUserType().equals(UserType.COMPANY_CLIENT) || userDetails.getUserType().equals(UserType.INDIVIDUAL_CLIENT);
    }
}
