package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;

import java.util.List;

public interface ReceiverInfoService {
    ReceiverInfoData get(ReceiverInfoOperationData receiverInfoOperationData);

    ReceiverInfoData edit(Long id, ReceiverInfoOperationData receiverInfoOperationData);

    ReceiverInfoData getById(Long id);

    List<ReceiverInfoData> getList();
}
