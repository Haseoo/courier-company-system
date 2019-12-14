package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.commandsdata.parcels.ReceiverInfoCommandData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.services.ports.ReceiverInfoService;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
                .map(receiverInfoData -> modelMapper.map(receiverInfoData, ReceiverInfoView.class))
                .collect(Collectors.toList());
    }

    @PutMapping
    public ReceiverInfoView getOrElseAdd(@RequestBody ReceiverInfoCommandData commandData) {
        return modelMapper.map(receiverInfoService.get(ReceiverInfoOperationData.of(commandData)), ReceiverInfoView.class);
    }

    @GetMapping("/{id}")
    public ReceiverInfoView getById(@PathVariable Long id) {
        return modelMapper.map(receiverInfoService.getById(id), ReceiverInfoView.class);
    }

    @PostMapping("/{id}")
    public ReceiverInfoView edit(@PathVariable Long id, @RequestBody ReceiverInfoCommandData commandData) {
        return modelMapper.map(receiverInfoService.edit(id, ReceiverInfoOperationData.of(commandData)), ReceiverInfoView.class);
    }
}
