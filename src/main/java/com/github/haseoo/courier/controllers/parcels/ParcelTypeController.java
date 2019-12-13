package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeOperationData;
import com.github.haseoo.courier.services.ports.ParcelTypeService;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeOfferView;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/parcelType")
public class ParcelTypeController {
    private final ParcelTypeService parcelTypeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<ParcelTypeView> getParcelType() {
        return parcelTypeService.getList(false)
                .stream()
                .map(parcelTypeData -> modelMapper
                        .map(parcelTypeData, ParcelTypeView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/offer")
    public List<ParcelTypeOfferView> getOffer() {
        return parcelTypeService.getList(true)
                .stream()
                .map(parcelTypeData -> modelMapper
                        .map(parcelTypeData, ParcelTypeOfferView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParcelTypeOfferView getById(@PathVariable Long id) {
        return modelMapper.map(parcelTypeService.getById(id), ParcelTypeOfferView.class);
    }

    @PutMapping
    public ParcelTypeView add(@RequestBody ParcelTypeCommandAddData commandData) {
        return modelMapper.map(parcelTypeService.add(ParcelTypeOperationData.of(commandData)), ParcelTypeView.class);
    }

    @PostMapping("/{id}")
    public ParcelTypeView edit(@PathVariable Long id, @RequestBody ParcelTypeCommandEditData commandData) {
        return modelMapper.map(parcelTypeService.edit(id, ParcelTypeOperationData.of(commandData)), ParcelTypeView.class);
    }
}
