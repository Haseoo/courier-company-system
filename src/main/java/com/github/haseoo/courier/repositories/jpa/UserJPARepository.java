package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<UserModel, Long> {
}
