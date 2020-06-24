package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateForCourierCommandData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandEditData;
import com.github.haseoo.courier.commandsdata.places.ParcelDateMoveCommandData;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.services.ports.ParcelService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import com.github.haseoo.courier.utilities.ParcelViewCreator;
import com.github.haseoo.courier.views.parcels.ParcelView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;
    private final ParcelStateService parcelStateService;
    private final ParcelViewCreator parcelViewCreator;

    @GetMapping()
    public ResponseEntity<List<ParcelView>> getList() {
        return new ResponseEntity<>(parcelService.getList()
                .stream()
                .map(parcelViewCreator::createParcelView)
                .collect(Collectors.toList()), OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ParcelView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelService.getById(id)), OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParcelView> add(@RequestBody @Valid ParcelCommandAddData commandAddData) {
        return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelService.add(ParcelAddData.of(commandAddData))), CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParcelView> edit(@PathVariable Long id, @RequestBody @Valid ParcelCommandEditData parcelEditData) {
        return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelService.edit(id, ParcelEditData.of(parcelEditData))), OK);
    }

    @PostMapping("/{id}/changeReceiver")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public ResponseEntity<ParcelView> markAsReturn(@PathVariable @Valid Long id) {
        return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelService.setParcelToReturn(id)), OK);
    }

    @PostMapping("/{id}/moveDate")
    public ResponseEntity<ParcelView> moveDate(@PathVariable Long id, @RequestBody @Valid ParcelDateMoveCommandData commandData) {
        return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelService.moveDate(id, commandData.getPin(), commandData.getNewDate())), OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CLIENT'})")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parcelService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


    @PostMapping("/{id}/changeState")
    @PreAuthorize("hasAnyRole({'ADMIN', 'COURIER'})")
    public ResponseEntity<ParcelView> changeStateForCourier(@PathVariable Long id, @RequestBody @Valid ParcelChangeStateForCourierCommandData commandData) {
        switch (commandData.getNewState()) {
            case DELIVERED:
                return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelStateService.setParcelAsDelivered(commandData.getCourierId(), id, commandData.getWasPaid())), OK);
            case RETURNED:
                return new ResponseEntity<>(parcelViewCreator.createParcelView(parcelStateService.setParcelReturned(commandData.getCourierId(), id, commandData.getWasPaid())), OK);
            default:
                throw new IllegalArgumentException(INVALID_ENUM_TYPE);
        }
    }

    @DeleteMapping("sate/{id}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    public ResponseEntity<Void> removeLastStRate(@PathVariable Long id) {
        parcelStateService.removeCurrentState(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
