package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.EmployeeService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/tests")
public class OrganolepticTestsController {
    private final CourierService courierService;
    private final LogisticianService logisticianService;
    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public List<EmployeeData> getList() {
        return employeeService.getList();
    }

    @PutMapping("/courier")
    public CourierData addCourier() {
        CourierData c = CourierData.builder()
                .userName("ovo")
                .password("o".toCharArray())
                .active(true)
                .name("oło")
                .surname("oŁowski")
                .phoneNumber("1234")
                .pesel("nextdday")
                .build();
        return courierService.add(c);
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
}
