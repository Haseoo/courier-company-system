package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.security.JwtAuthenticationResponse;
import com.github.haseoo.courier.security.JwtTokenProvider;
import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserLoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @PostMapping
    public Object login(@RequestBody LoginCommandData loginCommandData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCommandData.getUserName(),
                        String.valueOf(loginCommandData.getPassword())
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return UserLoginView.of(userService.getByUsername(loginCommandData.getUserName()),
                new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/oauth2")
    public Object login(@RequestBody ClientIndividualModel clientIndividualModel){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        clientIndividualModel.getUserName(),
                        String.valueOf(clientIndividualModel.getPassword())
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return UserLoginView.of(userService.getByUsername(clientIndividualModel.getUserName()),
                new JwtAuthenticationResponse(jwt));
    }
}
