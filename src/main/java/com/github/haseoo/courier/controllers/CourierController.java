package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.views.users.employees.CourierView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<CourierView> getList() {
        return courierService.getList()
                .stream()
                .map(courierModel -> modelMapper.map(courierModel, CourierView.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public CourierView getById(@PathVariable Long id) {
        return modelMapper.map(courierService.getById(id), CourierView.class);
    }

}
