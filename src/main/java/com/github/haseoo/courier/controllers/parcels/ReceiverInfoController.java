package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.services.ports.ReceiverInfoService;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/receiverInfo")
public class ReceiverInfoController {
    private final ReceiverInfoService receiverInfoService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<ReceiverInfoView> getReceiverInfo() {
        return receiverInfoService.getList()
                .stream()
                .map(ReceiverInfoView::of)
                .collect(Collectors.toList());
    }

    @PutMapping
    public ReceiverInfoView getOrElseAdd(@RequestBody ReceiverInfoCommandData commandData) {
        return ReceiverInfoView.of(receiverInfoService.get(ReceiverInfoOperationData.of(commandData)));
    }

    @GetMapping("/{id}")
    public ReceiverInfoView getById(@PathVariable Long id) {
        return ReceiverInfoView.of(receiverInfoService.getById(id));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReceiverInfoView edit(@PathVariable Long id, @RequestBody ReceiverInfoCommandData commandData) {
        return ReceiverInfoView.of(receiverInfoService.edit(id, ReceiverInfoOperationData.of(commandData)));
    }
}
