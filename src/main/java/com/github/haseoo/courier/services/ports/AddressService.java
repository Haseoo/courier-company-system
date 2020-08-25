package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;

import java.util.List;
import java.util.function.Consumer;

public interface AddressService {
    void consume(AddressOperationData addressOperationData, Consumer<AddressModel> consumer);

    List<AddressData> getList();
}
