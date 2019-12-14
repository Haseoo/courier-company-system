package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.querydata.AddressQueryData;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import com.github.haseoo.courier.servicedata.address.AddressData;
import com.github.haseoo.courier.servicedata.address.AddressOperationData;
import com.github.haseoo.courier.services.ports.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AddressData get(AddressOperationData addressOperationData) {
        return modelMapper.map(addressRepository
                .addressExist(AddressQueryData.of(addressOperationData))
                .orElseGet(() -> addressRepository
                        .saveAndFlush(modelMapper
                                .map(addressOperationData, AddressModel.class))
                ), AddressData.class);
    }

    @Override
    public List<AddressData> getList() {
        return addressRepository
                .getList()
                .stream()
                .map(addressModel -> modelMapper.map(addressModel, AddressData.class))
                .collect(Collectors.toList());
    }
}
