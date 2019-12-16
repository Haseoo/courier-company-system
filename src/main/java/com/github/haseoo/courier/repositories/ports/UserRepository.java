package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserModel> getList();

    Optional<UserModel> getById(Long id);

    Optional<UserModel> getByUsername(String username);

    UserModel saveAndFlush(UserModel userModel);
}
