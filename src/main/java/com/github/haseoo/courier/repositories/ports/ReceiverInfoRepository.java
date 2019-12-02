package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.querydata.ReceiverInfoQueryData;

import java.util.List;
import java.util.Optional;

public interface ReceiverInfoRepository {
    List<ReceiverInfoModel> getList();

    Optional<ReceiverInfoModel> getById(Long id);

    ReceiverInfoModel saveAndFlush(ReceiverInfoModel addressModel);

    Optional<ReceiverInfoModel> receiverInfoExists(ReceiverInfoQueryData receiverInfoQueryData);
}
