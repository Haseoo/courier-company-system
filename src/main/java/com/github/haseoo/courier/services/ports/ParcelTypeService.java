package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeOperationData;

import java.util.List;

public interface ParcelTypeService {
    ParcelTypeData add(ParcelTypeOperationData parcelType);

    ParcelTypeData edit(Long id, ParcelTypeOperationData parcelType);

    List<ParcelTypeData> getList(boolean active);

    ParcelTypeData getById(Long id);
}
