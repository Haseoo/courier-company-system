package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualAddCommandData;
import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualEditCommandData;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;
import com.github.haseoo.courier.services.ports.ClientIndividualService;
import com.github.haseoo.courier.views.users.clients.ClientIndividualView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/client/individual")
@RequiredArgsConstructor
public class ClientIndividualController {
    private final ClientIndividualService clientIndividualService;
    private final UserDetailsServiceImpl userDetalisService;

    @GetMapping("{id}")
    public ResponseEntity<ClientIndividualView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(ClientIndividualView.of(clientIndividualService.getById(id)), OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientIndividualView> add(@RequestBody @Valid ClientIndividualAddCommandData commandData) {
        return new ResponseEntity<>(ClientIndividualView.of(clientIndividualService.add(ClientIndividualAddData.of(commandData))), CREATED);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    @PutMapping("{id}")
    public ResponseEntity<ClientIndividualView> edit(@PathVariable Long id, @RequestBody @Valid ClientIndividualEditCommandData commandData) {
        userDetalisService.verifyEditResource(id);
        return new ResponseEntity<>(ClientIndividualView.of(clientIndividualService.edit(id, ClientIndividualEditData.of(commandData))), OK);
    }
}
