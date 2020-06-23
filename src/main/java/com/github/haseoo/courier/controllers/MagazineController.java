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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;

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
    public List<MagazineView> getList() {
        if (userDetailsService.isCurrentUserAdmin())
            return magazineService.getList().stream().map(MagazineView::of).collect(Collectors.toList());
        else
            return magazineService.getActiveList().stream().map(MagazineView::of).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @GetMapping("/{id}")
    public MagazineView getById(@PathVariable Long id) {
        return MagazineView.of(magazineService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public MagazineView add(@RequestBody MagazineAddCommandData commandData) {
        return MagazineView.of(magazineService.add(MagazineAddOperationData.of(commandData)));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PostMapping("/{id}")
    public MagazineView edit(@PathVariable Long id, @RequestBody MagazineEditCommandData commandData) {
        return MagazineView.of(magazineService.edit(id, MagazineEditOperationData.of(commandData)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/logisticians")
    public MagazineView addLogisiticians(@PathVariable Long id, @RequestBody MagazineAddLogisiticiansCommandData logisiticiansCommandData) {
        return MagazineView.of(magazineService.addLogisitcians(id, logisiticiansCommandData.getLogisiticiansIds()));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PostMapping("/{id}/parcels/add")
    public MagazineView addParcels(@PathVariable Long id, @RequestBody ParcelChangeStateMultipleCommandData commandData) {
        return MagazineView.of(parcelStateService.addParcelsToMagazine(id, commandData.getParcelsIds()));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    @PostMapping("/{id}/parcels")
    public List<ParcelView> getParcels(@PathVariable Long id, @RequestBody MagazineParcelFilerCommandData commandData) {
        switch (commandData.getType()) {
            case TO_RETURN:
                return magazineService.getParcelsToReturn(id)
                        .stream()
                        .map(parcelViewCreator::createParcelView)
                        .collect(Collectors.toList());
            case ASSIGNED_TO_MAGAZINE:
                return magazineService.getAssignedAtSenderParcels(id)
                        .stream()
                        .map(parcelViewCreator::createParcelView)
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }

}
