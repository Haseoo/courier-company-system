package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeAddOperationData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeEditOperationData;

import java.util.List;

public interface ParcelTypeService {
    ParcelTypeData add(ParcelTypeAddOperationData parcelType);

    ParcelTypeData edit(Long id, ParcelTypeEditOperationData parcelType);

    List<ParcelTypeData> getList(boolean active);

    ParcelTypeData getById(Long id);

    void delete(Long id);
}
