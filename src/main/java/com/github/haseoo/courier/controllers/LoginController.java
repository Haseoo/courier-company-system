package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.services.adapters.ClientIndividualServiceImpl;
import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserLoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.haseoo.courier.security.Constants.homeUrl;


@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final ClientIndividualServiceImpl clientIndividualService;


    @PostMapping
    public Object login(@RequestBody LoginCommandData loginCommandData) {
        return UserLoginView.of(userService.getByUsername(loginCommandData.getUserName()),
                userService.getJwt(loginCommandData));
    }

    @PostMapping("/oauth2")
    public Object login(@ModelAttribute("oauth2") ClientIndividualModel clientIndividualModel) {
        String token = clientIndividualService.signUp(clientIndividualModel);
        return UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("auth_token", token)
                .build().toUriString();
    }
}
