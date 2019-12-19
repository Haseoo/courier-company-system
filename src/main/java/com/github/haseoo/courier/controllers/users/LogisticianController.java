package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.LogisticianView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public List<LogisticianView> getList() {
        return logisticianService.getList()
                .stream()
                .map(LogisticianView::of)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    @GetMapping("{id}")
    public LogisticianView getById(@PathVariable Long id) {
        return LogisticianView.of(logisticianService.getById(id));
    }
}
