package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_ENUM_TYPE;

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
        if (addCommandData.getEmployeeType() == EmployeeType.COURIER) {
            return EmployeeView.of(courierService.add(EmployeeAddOperationData.of(addCommandData)));
        }
        if (addCommandData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return EmployeeView.of(logisticianService.add(EmployeeAddOperationData.of(addCommandData)));
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PostMapping("{id}")
    public EmployeeView add(@PathVariable Long id, @RequestBody EmployeeEditCommandData editOperationData) {
        userDetalisService.verifyEditResource(id);
        if (editOperationData.getEmployeeType() == EmployeeType.COURIER) {
            return EmployeeView.of(courierService.edit(id, EmployeeEditOperationData.of(editOperationData)));
        }
        if (editOperationData.getEmployeeType() == EmployeeType.LOGISTICIAN) {
            return EmployeeView.of(logisticianService.edit(id, EmployeeEditOperationData.of(editOperationData)));
        }
        throw new IllegalArgumentException(INVALID_ENUM_TYPE);
    }
}
