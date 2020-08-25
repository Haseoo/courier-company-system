package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.views.users.employees.LogisticianView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/employee/logistician")
@RequiredArgsConstructor
public class LogisticianController {
    private final LogisticianService logisticianService;

    @GetMapping
    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    public ResponseEntity<List<LogisticianView>> getList() {
        return new ResponseEntity<>(logisticianService.getList()
                .stream()
                .map(LogisticianView::of)
                .collect(Collectors.toList()), OK);
    }

    @PreAuthorize("hasAnyRole({'ADMIN', 'LOGISTICIAN'})")
    @GetMapping("{id}")
    public ResponseEntity<LogisticianView> getById(@PathVariable Long id) {
        return new ResponseEntity<>(LogisticianView.of(logisticianService.getById(id)), OK);
    }
}
