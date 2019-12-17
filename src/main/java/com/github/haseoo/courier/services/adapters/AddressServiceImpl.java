package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.querydata.AddressQueryData;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import com.github.haseoo.courier.services.ports.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void consume(AddressOperationData addressOperationData, Consumer<AddressModel> consumer) {
        consumer.accept(addressRepository
                .addressExist(AddressQueryData.of(addressOperationData))
                .orElseGet(() -> addressRepository
                        .saveAndFlush(modelMapper
                                .map(addressOperationData, AddressModel.class))
                ));
    }

    @Override
    public List<AddressData> getList() {
        return addressRepository
                .getList()
                .stream()
                .map(AddressData::of)
                .collect(Collectors.toList());
    }
}
