package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.MagazineDoesNotExist;
import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.repositories.ports.MagazineRepository;
import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;
import com.github.haseoo.courier.services.ports.AddressService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.services.ports.MagazineService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.utilities.Utils.copyNonNullProperties;

@Service
@RequiredArgsConstructor
public class MagazineServiceImpl implements MagazineService {
    private final MagazineRepository magazineRepository;
    private final LogisticianService logisticianService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Override
    public List<MagazineData> getList() {
        return magazineRepository.getList()
                .stream()
                .map(MagazineData::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MagazineData> getActiveList() {
        return magazineRepository.getActiveMagazines()
                .stream()
                .map(MagazineData::of)
                .collect(Collectors.toList());
    }

    @Override
    public MagazineData getById(Long id) {
        return MagazineData.of(magazineRepository.getById(id).orElseThrow(() -> new MagazineDoesNotExist(id)));
    }

    @Override
    @Transactional
    public MagazineData add(MagazineAddOperationData magazineAddOperationData) {
        MagazineModel magazineModel = new MagazineModel();
        magazineModel.setActive(true);
        addressService.consume(magazineAddOperationData.getAddress(), magazineModel::setAddress);
        return MagazineData.of(magazineRepository.saveAndFlush(magazineModel));
    }

    @Transactional
    @Override
    public MagazineData edit(Long id, MagazineEditOperationData magazineEditOperationData) {
        MagazineModel magazineModel = magazineRepository.getById(id).orElseThrow(() -> new MagazineDoesNotExist(id));
        copyNonNullProperties(modelMapper.map(magazineEditOperationData, MagazineModel.class), magazineModel);
        return MagazineData.of(magazineRepository.saveAndFlush(magazineModel));
    }

    @Transactional
    @Override
    public MagazineData addLogisitcians(Long magazineId, @NonNull List<Long> logisticiansIds) {
        MagazineModel magazineModel = magazineRepository.getById(magazineId).orElseThrow(() -> new MagazineDoesNotExist(magazineId));
        logisticianService.consumeAllById(logisticiansIds, logisticiasList -> {
            logisticiasList.forEach(logistician -> logistician.setMagazine(magazineModel));
            magazineModel.getLogisticians().addAll(logisticiasList);
        });
        return MagazineData.of(magazineRepository.saveAndFlush(magazineModel));

    }
}
