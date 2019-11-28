package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiverInfoJPARepository extends JpaRepository<ReceiverInfoModel, Long> {
    Optional<ReceiverInfoModel> findByNameAndSurnameAndEmailAddressAndPhoneNumber(String name, String surname, String emailAddress, String phoneNumber);
}
