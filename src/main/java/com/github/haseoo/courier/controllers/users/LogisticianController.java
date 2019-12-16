package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.LogisticianView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee/logistician")
@RequiredArgsConstructor
public class LogisticianController {
    private final LogisticianService logisticianService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<LogisticianView> getList() {
        return logisticianService.getList()
                .stream()
                .map(courierModel -> modelMapper.map(courierModel, LogisticianView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public LogisticianView getById(@PathVariable Long id) {
        return modelMapper.map(logisticianService.getById(id), LogisticianView.class);
    }
}
