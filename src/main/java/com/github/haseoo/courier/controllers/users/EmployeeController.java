package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.views.users.employees.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

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
    public ResponseEntity<EmployeeView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(EmployeeView.of(employeeService.getById(id)), OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeView> add(@RequestBody @Valid EmployeeAddCommandData addCommandData) {
        return new ResponseEntity<>(EmployeeView.of(employeeService.addEmployee(addCommandData)), CREATED);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN', 'COURIER'})")
    @PutMapping("{id}")
    public ResponseEntity<EmployeeView> edit(@PathVariable Long id, @RequestBody @Valid EmployeeEditCommandData editOperationData) {
        return new ResponseEntity<>(EmployeeView.of(employeeService.editEmployee(id, editOperationData)), OK);
    }
}
