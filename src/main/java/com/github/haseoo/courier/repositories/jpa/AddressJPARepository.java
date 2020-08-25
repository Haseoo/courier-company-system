package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressJPARepository extends JpaRepository<AddressModel, Long> {
    Optional<AddressModel> findByPostalCodeAndCityAndStreetAndBuildingNumberAndFlatNumber(String postalCode,
                                                                                          String city,
                                                                                          String street,
                                                                                          String buildingNumber,
                                                                                          String flatNumber);
}
