package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.AddressModel;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AddressRepository {
    List<AddressModel> getList();
    Optional<AddressModel> getById(Integer id);
    AddressModel saveAndFlush(AddressModel addressModel);
}
