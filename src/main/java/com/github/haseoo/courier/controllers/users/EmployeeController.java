package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CourierService courierService;
    private final LogisticianService logisticianService;
    private final UserDetailsServiceImpl userDetalisService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<EmployeeView> getList() {
        return employeeService.getList()
                .stream()
                .map(EmployeeView::of)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public EmployeeView getById(@PathVariable Long id) {
        return EmployeeView.of(employeeService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public EmployeeView add(@RequestBody EmployeeAddCommandData addCommandData) {
        return EmployeeView.of(employeeService.addEmployee(addCommandData));
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PostMapping("{id}")
    public EmployeeView add(@PathVariable Long id, @RequestBody EmployeeEditCommandData editOperationData) {
        return EmployeeView.of(employeeService.editEmployee(id, editOperationData));
    }
}
