package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.ParcelTypeNotFound;
import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.repositories.ports.ParcelTypeRepository;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeOperationData;
import com.github.haseoo.courier.services.ports.ParcelTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class ParcelTypeServiceImpl implements ParcelTypeService {
    private final ParcelTypeRepository parcelTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ParcelTypeData add(ParcelTypeOperationData parcelType) {
        return modelMapper.map(parcelTypeRepository.saveAndFlush(modelMapper.map(parcelType, ParcelTypeModel.class)),
                ParcelTypeData.class);
    }

    @Override
    public ParcelTypeData edit(Long id, ParcelTypeOperationData parcelType) {
        ParcelTypeModel parcelTypeModel = parcelTypeRepository.getById(id).orElseThrow(() -> new ParcelTypeNotFound(id));
        copyNonNullProperties(modelMapper.map(parcelType, ParcelTypeModel.class), parcelTypeModel);
        return modelMapper.map(parcelTypeRepository.saveAndFlush(parcelTypeModel), ParcelTypeData.class);
    }

    @Override
    public List<ParcelTypeData> getList(boolean offer) {
        List<ParcelTypeModel> parcelModels;
        if (offer) {
            parcelModels = parcelTypeRepository.getActiveTypes();
        } else {
            parcelModels = parcelTypeRepository.getList();
        }
        return parcelModels.stream()
                .map(parcelTypeModel -> modelMapper.map(parcelTypeModel, ParcelTypeData.class))
                .collect(Collectors.toList());
    }

    @Override
    public ParcelTypeData getById(Long id) {
        ParcelTypeModel parcelTypeModel = parcelTypeRepository.getById(id).orElseThrow(() -> new ParcelTypeNotFound(id));
        return modelMapper.map(parcelTypeModel, ParcelTypeData.class);
    }
}
