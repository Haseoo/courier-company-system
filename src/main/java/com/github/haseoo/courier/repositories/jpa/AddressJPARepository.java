package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressJPARepository extends JpaRepository<AddressModel, Integer> {
}
