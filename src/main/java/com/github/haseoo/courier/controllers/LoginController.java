package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserLoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    @PostMapping
    public UserLoginView login(@RequestBody LoginCommandData loginCommandData) {
        return UserLoginView.of(userService.login(loginCommandData.getUserName(), loginCommandData.getPassword()));
    }
}
