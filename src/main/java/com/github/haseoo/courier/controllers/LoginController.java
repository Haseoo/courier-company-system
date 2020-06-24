package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.services.adapters.ClientIndividualServiceImpl;
import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserLoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final ClientIndividualServiceImpl clientIndividualService;


    @PostMapping
    public Object login(@RequestBody @Valid LoginCommandData loginCommandData) {
        return UserLoginView.of(userService.getByUsername(loginCommandData.getUserName()),
                userService.getJwt(loginCommandData));
    }
}
