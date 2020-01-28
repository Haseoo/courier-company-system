package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateForCourierCommandData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandAddData;
import com.github.haseoo.courier.commandsdata.places.ParcelDateMoveCommandData;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.services.ports.ParcelService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.utilities.ParcelViewCreator;
import com.github.haseoo.courier.views.parcels.ParcelView;
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
    private final ParcelViewCreator parcelViewCreator;

    @GetMapping()
    public List<ParcelView> getList() {
        return parcelService.getList()
                .stream()
                .map(parcelViewCreator::createParcelView)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public ParcelView getById(@PathVariable Long id) {
        return parcelViewCreator.createParcelView(parcelService.getById(id));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParcelView add(@RequestBody ParcelCommandAddData commandAddData) {
        return parcelViewCreator.createParcelView(parcelService.add(ParcelAddData.of(commandAddData)));
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParcelView edit(@PathVariable Long id, @RequestBody ParcelEditData parcelEditData) {
        return parcelViewCreator.createParcelView(parcelService.edit(id, parcelEditData));
    }

    @PostMapping("/{id}/changeReceiver")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public ParcelView markAsReturn(@PathVariable Long id) {
        return parcelViewCreator.createParcelView(parcelService.setParcelToReturn(id));
    }

    @PostMapping("/{id}/moveDate")
    public ParcelView moveDate(@PathVariable Long id, @RequestBody ParcelDateMoveCommandData commandData) {
        return parcelViewCreator.createParcelView(parcelService.moveDate(id, commandData.getPin(), commandData.getNewDate()));
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    public void delete(@PathVariable Long id) {
        parcelService.delete(id);
    }


    @PostMapping("/{id}/changeState")
    @PreAuthorize("hasAnyRole({'ADMIN', 'COURIER'})")
    public ParcelView changeStateForCourier(@PathVariable Long id, @RequestBody ParcelChangeStateForCourierCommandData commandData) {
        switch (commandData.getNewState()) {
            case DELIVERED:
                return parcelViewCreator.createParcelView(parcelStateService.setParcelAsDelivered(commandData.getCourierId(), id, commandData.getWasPaid()));
            case RETURNED:
                return parcelViewCreator.createParcelView(parcelStateService.setParcelReturned(commandData.getCourierId(), id, commandData.getWasPaid()));
            default:
                throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }

    @DeleteMapping("sate/{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    public void removeLastState(@PathVariable Long id) {
        parcelStateService.removeCurrentState(id);
    }
}
