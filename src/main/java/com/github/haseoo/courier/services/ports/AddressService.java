package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.address.AddressData;
import com.github.haseoo.courier.servicedata.address.AddressOperationData;

import java.util.List;

public interface AddressService {
    AddressData get(AddressOperationData addressOperationData);

    List<AddressData> getList();
}
