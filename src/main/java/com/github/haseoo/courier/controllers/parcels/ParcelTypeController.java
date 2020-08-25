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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/parcelType")
public class ParcelTypeController {
    private final ParcelTypeService parcelTypeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ParcelTypeView>> getParcelType() {
        return new ResponseEntity<>(parcelTypeService.getList(false)
                .stream()
                .map(ParcelTypeView::of)
                .collect(Collectors.toList()), OK);
    }

    @GetMapping("/offer")
    public ResponseEntity<List<ParcelTypeOfferView>> getOffer() {
        return new ResponseEntity<>(parcelTypeService.getList(true)
                .stream()
                .map(ParcelTypeOfferView::of)
                .collect(Collectors.toList()), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelTypeOfferView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(ParcelTypeOfferView.of(parcelTypeService.getById(id)), OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParcelTypeView> add(@RequestBody @Valid ParcelTypeCommandAddData commandData) {
        return new ResponseEntity<>(ParcelTypeView.of(parcelTypeService.add(ParcelTypeAddOperationData.of(commandData))), CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParcelTypeView> edit(@PathVariable Long id, @RequestBody @Valid ParcelTypeCommandEditData commandData) {
        return new ResponseEntity<>(ParcelTypeView.of(parcelTypeService.edit(id, ParcelTypeEditOperationData.of(commandData))), OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parcelTypeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
