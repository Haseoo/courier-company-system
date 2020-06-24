package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateMultipleCommandData;
import com.github.haseoo.courier.commandsdata.places.MagazineAddCommandData;
import com.github.haseoo.courier.commandsdata.places.MagazineAddLogisiticiansCommandData;
import com.github.haseoo.courier.commandsdata.places.MagazineEditCommandData;
import com.github.haseoo.courier.commandsdata.places.MagazineParcelFilerCommandData;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;
import com.github.haseoo.courier.services.ports.MagazineService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.utilities.ParcelViewCreator;
import com.github.haseoo.courier.views.parcels.ParcelView;
import com.github.haseoo.courier.views.places.MagazineView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/magazine")
@RequiredArgsConstructor
public class MagazineController {
    private final MagazineService magazineService;
    private final ParcelStateService parcelStateService;
    private final ParcelViewCreator parcelViewCreator;
    private final UserDetailsServiceImpl userDetailsService;

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @GetMapping
    public ResponseEntity<List<MagazineView>> getList() {
        if (userDetailsService.isCurrentUserAdmin())
            return new ResponseEntity<>(magazineService.getList().stream().map(MagazineView::of).collect(Collectors.toList()), OK);
        else
            return new ResponseEntity<>(magazineService.getActiveList().stream().map(MagazineView::of).collect(Collectors.toList()), OK);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @GetMapping("/{id}")
    public ResponseEntity<MagazineView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(MagazineView.of(magazineService.getById(id)), OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MagazineView> add(@RequestBody @Valid MagazineAddCommandData commandData) {
        return new ResponseEntity<>(MagazineView.of(magazineService.add(MagazineAddOperationData.of(commandData))), CREATED);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PutMapping("/{id}")
    public ResponseEntity<MagazineView> edit(@PathVariable Long id, @RequestBody @Valid MagazineEditCommandData commandData) {
        return new ResponseEntity<>(MagazineView.of(magazineService.edit(id, MagazineEditOperationData.of(commandData))), OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/logisticians")
    public ResponseEntity<MagazineView> addLogisiticians(@PathVariable Long id, @RequestBody @Valid MagazineAddLogisiticiansCommandData logisiticiansCommandData) {
        return new ResponseEntity<>(MagazineView.of(magazineService.addLogisitcians(id, logisiticiansCommandData.getLogisiticiansIds())), OK);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PostMapping("/{id}/parcels/add")
    public ResponseEntity<MagazineView> addParcels(@PathVariable Long id, @RequestBody @Valid ParcelChangeStateMultipleCommandData commandData) {
        return new ResponseEntity<>(MagazineView.of(parcelStateService.addParcelsToMagazine(id, commandData.getParcelsIds())), OK);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    @PostMapping("/{id}/parcels")
    public ResponseEntity<List<ParcelView>> getParcels(@PathVariable Long id, @RequestBody @Valid MagazineParcelFilerCommandData commandData) {
        switch (commandData.getType()) {
            case TO_RETURN:
                return new ResponseEntity<>(magazineService.getParcelsToReturn(id)
                        .stream()
                        .map(parcelViewCreator::createParcelView)
                        .collect(Collectors.toList()),
                        OK);
            case ASSIGNED_TO_MAGAZINE:
                return new ResponseEntity<>(magazineService.getAssignedAtSenderParcels(id)
                        .stream()
                        .map(parcelViewCreator::createParcelView)
                        .collect(Collectors.toList()),
                        OK);
            default:
                throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }

}
