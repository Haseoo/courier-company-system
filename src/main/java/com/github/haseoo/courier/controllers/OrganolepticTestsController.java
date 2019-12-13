package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.CourierOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.services.ports.UserService;
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

    @GetMapping("/employee")
    public List<EmployeeData> getList() {
        return employeeService.getList();
    }

    @PutMapping("/courier")
    public CourierData addCourier() {
        CourierOperationData c = CourierOperationData.builder()
                .userName("ovo")
                .password("o".toCharArray())
                .active(true)
                .name("oło")
                .surname("oŁowski")
                .phoneNumber("98072811859")
                .pesel("98072811859")
                .build();
        return courierService.add(c);
    }

    @PostMapping("/courier/{id}")
    public CourierData editCourier(@PathVariable Long id) {
        CourierOperationData c = CourierOperationData.builder()
                .userName("ovo")
                .password("onononono".toCharArray())
                .active(true)
                .name("oło")
                .surname("oŁowskie")
                .phoneNumber("98072811859")
                .pesel("98072811859")
                .build();
        return courierService.edit(id, c);
    }

    @GetMapping("/courier/{id}")
    public CourierData getCourier(@PathVariable Long id) {
        return courierService.getById(id);
    }

    @PutMapping("/logistician")
    public LogisticianData addLogistician() {
        LogisticianData c = LogisticianData.builder()
                .userName("ovo")
                .password("o".toCharArray())
                .active(true)
                .name("oło")
                .surname("oŁowski")
                .phoneNumber("1234")
                .pesel("nextdday")
                .build();
        return logisticianService.add(c);
    }

    @DeleteMapping("/user{id}")
    public UserData deleteUser(@PathVariable Long id) {
        return userService.setAsInactive(id);
    }
}
