package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUserName(String username);
}
