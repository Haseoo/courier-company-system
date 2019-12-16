package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;

import java.util.List;
import java.util.function.Consumer;

public interface ReceiverInfoService {
    ReceiverInfoData get(ReceiverInfoOperationData receiverInfoOperationData);

    void consume(ReceiverInfoOperationData receiverInfoOperationData, Consumer<ReceiverInfoModel> consumer);

    ReceiverInfoData edit(Long id, ReceiverInfoOperationData receiverInfoOperationData);

    ReceiverInfoData getById(Long id);

    List<ReceiverInfoData> getList();
}
