package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeAddOperationData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeEditOperationData;
import com.github.haseoo.courier.services.ports.ParcelTypeService;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeOfferView;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
                .map(ParcelTypeView::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/offer")
    public List<ParcelTypeOfferView> getOffer() {
        return parcelTypeService.getList(true)
                .stream()
                .map(ParcelTypeOfferView::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParcelTypeOfferView getById(@PathVariable Long id) {
        return ParcelTypeOfferView.of(parcelTypeService.getById(id));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ParcelTypeView add(@RequestBody ParcelTypeCommandAddData commandData) {
        return ParcelTypeView.of(parcelTypeService.add(ParcelTypeAddOperationData.of(commandData)));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ParcelTypeView edit(@PathVariable Long id, @RequestBody ParcelTypeCommandEditData commandData) {
        return ParcelTypeView.of(parcelTypeService.edit(id, ParcelTypeEditOperationData.of(commandData)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        parcelTypeService.delete(id);
    }
}
