package com.github.haseoo.courier.controllers;

import com.github.haseoo.courier.services.adapters.PolishPostalCodeHelperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/tests")
public class OrganolepticTestsController {
    private final PolishPostalCodeHelperImpl polishCodeHelper;

    @GetMapping
    public Boolean testPostalCode() throws IOException {
        return polishCodeHelper.isPostalCodeInCity("25-123", "Kielce");
    }

}
