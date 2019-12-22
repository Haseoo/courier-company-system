package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateForCourierCommandData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.services.ports.ParcelService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.views.parcels.ParcelViewAfterAddOrEdit;
import com.github.haseoo.courier.views.parcels.ParcelViewForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;
    private final ParcelStateService parcelStateService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ParcelViewForAdmin> getListForAdmin() {
        return parcelService.getList()
                .stream()
                .map(ParcelViewForAdmin::of)
                .collect(Collectors.toList());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParcelViewAfterAddOrEdit add(@RequestBody ParcelCommandAddData commandAddData) {
        return ParcelViewAfterAddOrEdit.of(parcelService.add(ParcelAddData.of(commandAddData)));
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParcelViewAfterAddOrEdit edit(@PathVariable Long id, @RequestBody ParcelEditData parcelEditData) {
        return ParcelViewAfterAddOrEdit.of(parcelService.edit(id, parcelEditData));
    }

    @PostMapping("/{id}/changeReceiver")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public ParcelViewAfterAddOrEdit edit(@PathVariable Long id, @RequestBody ReceiverInfoCommandData newReceiver) {
        return ParcelViewAfterAddOrEdit.of(parcelService.changeReceiverForLogistician(id, ReceiverInfoOperationData.of(newReceiver)));
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    public void delete(@PathVariable Long id) {
        parcelService.delete(id);
    }


    @PostMapping("/{id}/changeState")
    @PreAuthorize("hasAnyRole({'ADMIN', 'COURIER'})")
    public ParcelViewAfterAddOrEdit changeStateForCourier(@PathVariable Long id, @RequestBody ParcelChangeStateForCourierCommandData commandData) {
        switch (commandData.getNewState()) {
            case DELIVERED:
                return ParcelViewAfterAddOrEdit.of(parcelStateService.setParcelAsDelivered(commandData.getCourierId(), id));
            case RETURNED:
                return ParcelViewAfterAddOrEdit.of(parcelStateService.setParcelReturned(commandData.getCourierId(), id));
            default:
                throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }
}
