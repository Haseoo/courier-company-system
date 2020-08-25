package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.EstimatedDeliveryTimeModel;
import com.github.haseoo.courier.repositories.jpa.EstimatedDeliveryTimeJPARepository;
import com.github.haseoo.courier.repositories.ports.EstimatedDeliveryTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class EstimatedDeliveryTimeRepositoryImpl implements EstimatedDeliveryTimeRepository {
    private final EstimatedDeliveryTimeJPARepository estimatedDeliveryTimeJPARepository;


    public EstimatedDeliveryTimeModel saveAndFlush(EstimatedDeliveryTimeModel estimatedDeliveryTimeModel) {
        return estimatedDeliveryTimeJPARepository.saveAndFlush(estimatedDeliveryTimeModel);
    }

    @Override
    public EstimatedDeliveryTimeModel getById(Long id) {
        return estimatedDeliveryTimeJPARepository.getEstimatedDeliveryTimeModelById(id);
    }

}
