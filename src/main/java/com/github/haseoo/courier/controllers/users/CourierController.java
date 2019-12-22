package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateMultipleCommandData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.views.users.employees.CourierView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;
    private final ParcelStateService parcelStateService;

    @GetMapping
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    public List<CourierView> getList() {
        return courierService.getList()
                .stream()
                .map(CourierView::of)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    public CourierView getById(@PathVariable Long id) {
        return CourierView.of(courierService.getById(id));
    }

    @PostMapping("/{id}/parcelAssign")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public CourierView assignParcels(@PathVariable Long id, @RequestBody ParcelChangeStateMultipleCommandData commandData) {
        return CourierView.of(parcelStateService.assignParcelsToCourier(id, commandData.getParcelsIds()));
    }

    @PostMapping("/{id}/parcelPickup")
    @PreAuthorize("hasAnyRole({'ADMIN', 'COURIER'})")
    public CourierView pickupParcels(@PathVariable Long id, @RequestBody ParcelChangeStateMultipleCommandData commandData) {
        return CourierView.of(parcelStateService.setAsPickedByCourier(id, commandData.getParcelsIds()));
    }

}
