package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.UserData;

import java.util.List;

public interface UserService {
    List<UserData> getList();

    UserData setAsInactive(Long id);

    UserData setAsActive(Long id);

    void checkUsername(String username);
}
