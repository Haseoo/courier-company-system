package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.security.JwtAuthenticationResponse;
import com.github.haseoo.courier.servicedata.users.UserData;

import java.util.List;

public interface UserService {
    List<UserData> getList();

    UserData setAsInactive(Long id);

    UserData setAsActive(Long id);

    UserData getByUsername(String userName);

    JwtAuthenticationResponse getJwt(LoginCommandData loginCommandData);

    void checkUsername(String username);
}
