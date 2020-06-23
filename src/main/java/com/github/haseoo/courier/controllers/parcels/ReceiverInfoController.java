package com.github.haseoo.courier.controllers.parcels;

import com.github.haseoo.courier.services.ports.ReceiverInfoService;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ReceiverInfoView getById(@PathVariable Long id) {
        return ReceiverInfoView.of(receiverInfoService.getById(id));
    }
}
