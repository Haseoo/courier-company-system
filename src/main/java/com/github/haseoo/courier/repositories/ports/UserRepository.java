package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.UserModel;

import java.util.List;

public interface UserRepository {
    List<UserModel> getList();
}
