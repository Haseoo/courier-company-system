package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.repositories.querydata.AddressQueryData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AddressRepository {
    List<AddressModel> getList();
    Optional<AddressModel> getById(Long id);
    AddressModel saveAndFlush(AddressModel addressModel);
    Boolean addressExist(AddressQueryData addressQueryData);
}
