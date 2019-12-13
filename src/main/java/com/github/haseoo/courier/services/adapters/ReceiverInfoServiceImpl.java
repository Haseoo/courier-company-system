package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.ReceiverInfoNotFound;
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
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final ReceiverInfoRepository receiverInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReceiverInfoData get(ReceiverInfoOperationData receiverInfoOperationData) {
        return modelMapper.map(receiverInfoRepository
                .receiverInfoExists(ReceiverInfoQueryData.of(receiverInfoOperationData))
                .orElse(receiverInfoRepository
                        .saveAndFlush(modelMapper
                                .map(receiverInfoOperationData, ReceiverInfoModel.class)
                        )
                ), ReceiverInfoData.class);
    }

    @Override
    public ReceiverInfoData edit(Long id, ReceiverInfoOperationData receiverInfoOperationData) {
        ReceiverInfoModel receiverInfoModel = receiverInfoRepository.getById(id).orElseThrow(() -> new ReceiverInfoNotFound(id));
        copyNonNullProperties(modelMapper.map(receiverInfoOperationData, ReceiverInfoModel.class),receiverInfoModel);
        return modelMapper.map(receiverInfoRepository.saveAndFlush(receiverInfoModel),ReceiverInfoData.class);
    }

    @Override
    public ReceiverInfoData getById(Long id) {
        ReceiverInfoModel receiverInfoModel = receiverInfoRepository.getById(id).orElseThrow(() -> new ReceiverInfoNotFound(id));
        return modelMapper.map(receiverInfoModel,ReceiverInfoData.class);
    }

    @Override
    public List<ReceiverInfoData> getList() {
        return receiverInfoRepository.getList()
                .stream()
                .map(receiverInfoModel -> modelMapper.map(receiverInfoModel,ReceiverInfoData.class))
                .collect(Collectors.toList());
    }
}
