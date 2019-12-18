package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualAddCommandData;
import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualEditCommandData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;
import com.github.haseoo.courier.services.ports.ClientIndividualService;
import com.github.haseoo.courier.views.users.clients.ClientIndividualView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/individual")
@RequiredArgsConstructor
public class ClientIndividualController {
    private final ClientIndividualService clientIndividualService;

    @GetMapping("{id}")
    public ClientIndividualView getById(@PathVariable Long id) {
        return ClientIndividualView.of(clientIndividualService.getById(id));
    }

    @PutMapping
    public ClientIndividualView add(@RequestBody ClientIndividualAddCommandData commandData) {
        return ClientIndividualView.of(clientIndividualService.add(ClientIndividualAddData.of(commandData)));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    @PostMapping("{id}")
    public ClientIndividualView edit(@PathVariable Long id, @RequestBody ClientIndividualEditCommandData commandData) {
        return ClientIndividualView.of(clientIndividualService.edit(id, ClientIndividualEditData.of(commandData)));
    }
}
