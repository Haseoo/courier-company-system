package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeCannotBeRemoved;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeFeeCannotBeChanged;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeNotFound;
import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.repositories.ports.ParcelTypeRepository;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeAddOperationData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeEditOperationData;
import com.github.haseoo.courier.services.ports.ParcelTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class ParcelTypeServiceImpl implements ParcelTypeService {
    private final ParcelTypeRepository parcelTypeRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ParcelTypeData add(ParcelTypeAddOperationData parcelType) {
        return ParcelTypeData.of(parcelTypeRepository.saveAndFlush(modelMapper.map(parcelType, ParcelTypeModel.class)));
    }

    @Transactional
    @Override
    public ParcelTypeData edit(Long id, ParcelTypeEditOperationData parcelType) {
        ParcelTypeModel parcelTypeModel = parcelTypeRepository.getById(id).orElseThrow(() -> new ParcelTypeNotFound(id));
        if (parcelType.getPrice() != null && isAnyUnpaidParcel(parcelTypeModel)) {
            throw new ParcelTypeFeeCannotBeChanged(parcelTypeModel.getName());
        }
        copyNonNullProperties(modelMapper.map(parcelType, ParcelTypeModel.class), parcelTypeModel);
        return ParcelTypeData.of(parcelTypeRepository.saveAndFlush(parcelTypeModel));
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
                .map(ParcelTypeData::of)
                .collect(Collectors.toList());
    }

    @Override
    public ParcelTypeData getById(Long id) {
        return ParcelTypeData.of(parcelTypeRepository.getById(id).orElseThrow(() -> new ParcelTypeNotFound(id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ParcelTypeModel parcelTypeModel = parcelTypeRepository.getById(id).orElseThrow(() -> new ParcelTypeNotFound(id));
        if (!parcelTypeModel.getParcels().isEmpty()) {
            throw new ParcelTypeCannotBeRemoved(parcelTypeModel.getName());
        }
        parcelTypeRepository.delete(id);
    }


    private boolean isAnyUnpaidParcel(ParcelTypeModel parcelTypeModel) {
        return parcelTypeModel.getParcels().stream().anyMatch(parcelModel -> !parcelModel.getPaid());
    }
}
