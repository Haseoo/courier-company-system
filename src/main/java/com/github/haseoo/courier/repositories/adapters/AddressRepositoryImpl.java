package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.repositories.jpa.AddressJPARepository;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import com.github.haseoo.courier.repositories.querydata.AddressQueryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final AddressJPARepository addressJPARepository;
    @Override
    public List<AddressModel> getList() {
        return addressJPARepository.findAll();
    }

    @Override
    public Optional<AddressModel> getById(Long id) {
        return addressJPARepository.findById(id);
    }

    @Override
    public AddressModel saveAndFlush(AddressModel addressModel) {
        return addressJPARepository.saveAndFlush(addressModel);
    }

    @Override
    public Boolean addressExist(AddressQueryData addressQueryData) {
        return addressJPARepository
                .findByPostalCodeAndCityAndStreetAndBuildingNumberAndFlatNumber(
                        addressQueryData.getPostalCode(),
                        addressQueryData.getCity(),
                        addressQueryData.getStreet(),
                        addressQueryData.getBuildingNumber(),
                        addressQueryData.getFlatNumber()
                ).isPresent();
    }
}
