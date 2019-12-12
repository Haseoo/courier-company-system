package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.repositories.jpa.UserJPARepository;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJPARepository userJPARepository;

    @Override
    public List<UserModel> getList() {
        return userJPARepository.findAll();
    }

    @Override
    public Optional<UserModel> getById(Long id) {
        return userJPARepository.findById(id);
    }

    @Override
    public UserModel saveAndFlush(UserModel userModel) {
        return userJPARepository.saveAndFlush(userModel);
    }
}
