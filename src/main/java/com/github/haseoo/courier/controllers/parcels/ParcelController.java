package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ParcelCommandAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.services.ports.ParcelService;
import com.github.haseoo.courier.views.places.ParcelViewAfterAddOrEdit;
import com.github.haseoo.courier.views.places.ParcelViewForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;

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
}
