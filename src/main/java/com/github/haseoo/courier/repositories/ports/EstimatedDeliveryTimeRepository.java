package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.EstimatedDeliveryTimeModel;

public interface EstimatedDeliveryTimeRepository {

    EstimatedDeliveryTimeModel getById(Long id);

    EstimatedDeliveryTimeModel saveAndFlush(EstimatedDeliveryTimeModel estimatedDeliveryTimeModel);

}
