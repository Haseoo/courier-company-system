package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.clients.ClientCompanyAddCommandData;
import com.github.haseoo.courier.commandsdata.users.clients.ClientCompanyEditCommandData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyEditData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
import com.github.haseoo.courier.views.users.clients.ClientCompanyView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/company")
@RequiredArgsConstructor
public class ClientCompanyController {
    private final ClientCompanyService clientCompanyService;

    @GetMapping("{id}")
    public ClientCompanyView getById(@PathVariable Long id) {
        return ClientCompanyView.of(clientCompanyService.getById(id));
    }

    @PutMapping
    public ClientCompanyView add(@RequestBody ClientCompanyAddCommandData commandData) {
        return ClientCompanyView.of(clientCompanyService.add(ClientCompanyAddData.of(commandData)));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    @PostMapping("{id}")
    public ClientCompanyView edit(@PathVariable Long id,
                                  @RequestBody ClientCompanyEditCommandData commandData) {
        return ClientCompanyView.of(clientCompanyService.edit(id, ClientCompanyEditData.of(commandData)));
    }
}
