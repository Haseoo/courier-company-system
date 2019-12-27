package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.ClientService;
import com.github.haseoo.courier.views.users.clients.ClientView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<ClientView> getList() {
        return clientService.getList().stream().map(ClientView::of).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientView getById(@PathVariable Long id) {
        return ClientView.of(clientService.getById(id));
    }
}
