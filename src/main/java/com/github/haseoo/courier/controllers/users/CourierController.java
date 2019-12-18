package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.views.users.employees.CourierView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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

}
