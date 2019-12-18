package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserView> getList() {
        return userService.getList().stream().map(UserView::of).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{id}")
    public UserView setAsActive(@PathVariable Long id) {
        return UserView.of(userService.setAsActive(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public UserView setAsInactive(@PathVariable Long id) {
        return UserView.of(userService.setAsInactive(id));
    }
}
