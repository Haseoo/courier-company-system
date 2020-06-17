package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ReceiverInfoNotFound;
import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.querydata.ReceiverInfoQueryData;
import com.github.haseoo.courier.repositories.ports.ReceiverInfoRepository;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.services.ports.ReceiverInfoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;
import static com.github.haseoo.courier.utilities.Utils.validateEmailAddress;

@Service
@RequiredArgsConstructor
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final ReceiverInfoRepository receiverInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public void consume(ReceiverInfoOperationData receiverInfoOperationData, Consumer<ReceiverInfoModel> consumer) {
        validateEmailAddress(receiverInfoOperationData.getEmailAddress());
        consumer.accept(receiverInfoRepository
                .receiverInfoExists(ReceiverInfoQueryData.of(receiverInfoOperationData))
                .orElseGet(() -> receiverInfoRepository
                        .saveAndFlush(modelMapper
                                .map(receiverInfoOperationData, ReceiverInfoModel.class)
                        )
                )
        );
    }

    @Override
    public ReceiverInfoData getById(Long id) {
        return ReceiverInfoData.of(receiverInfoRepository.getById(id).orElseThrow(() -> new ReceiverInfoNotFound(id)));
    }

    @Override
    public List<ReceiverInfoData> getList() {
        return receiverInfoRepository.getList()
                .stream()
                .map(ReceiverInfoData::of)
                .collect(Collectors.toList());
    }
}
