package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;

import java.util.List;

public interface ParcelService {
    ParcelData add(ParcelAddData parcelAddData);

    ParcelData edit(Long id, ParcelEditData parcelEditData);

    ParcelData changeReceiverForLogistician(Long id, ReceiverInfoOperationData receiverInfoOperationData);

    List<ParcelData> getList();

    void delete(Long id);
}
