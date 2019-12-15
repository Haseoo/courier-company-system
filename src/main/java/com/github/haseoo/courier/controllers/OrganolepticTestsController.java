package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.servicedata.address.AddressData;
import com.github.haseoo.courier.servicedata.address.AddressOperationData;
import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.servicedata.users.employees.*;
import com.github.haseoo.courier.services.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/tests")
public class OrganolepticTestsController {
    private final CourierService courierService;
    private final LogisticianService logisticianService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping("/employee")
    public List<EmployeeData> getList() {
        return employeeService.getList();
    }

    @PutMapping("/courier")
    public CourierData addCourier(@RequestBody EmployeeAddOperationData courierAddOperationData) {
        return courierService.add(courierAddOperationData);
    }

    @PostMapping("/courier/{id}")
    public CourierData editCourier(@PathVariable Long id, @RequestBody EmployeeEditOperationData c) {
        return courierService.edit(id, c);
    }

    @GetMapping("/courier/{id}")
    public CourierData getCourier(@PathVariable Long id) {
        return courierService.getById(id);
    }

    @PutMapping("/logistician")
    public LogisticianData addLogistician(@RequestBody EmployeeAddOperationData c) {
        return logisticianService.add(c);
    }

    @DeleteMapping("/user{id}")
    public UserData deleteUser(@PathVariable Long id) {
        return userService.setAsInactive(id);
    }

    @GetMapping("/address")
    public List<AddressData> getListAddress() {
        return addressService.getList();
    }

    @PutMapping
    public AddressData getAddress(@RequestBody AddressOperationData addressOperationData) {
        return addressService.get(addressOperationData);
    }
}
