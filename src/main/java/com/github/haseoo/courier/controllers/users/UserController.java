package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserView> getList() {
        return userService.getList().stream().map(UserView::of).collect(Collectors.toList());
    }

    @PostMapping("{id}")
    public UserView setAsActive(@PathVariable Long id) {
        return UserView.of(userService.setAsActive(id));
    }

    @DeleteMapping("{id}")
    public UserView setAsInactive(@PathVariable Long id) {
        return UserView.of(userService.setAsInactive(id));
    }
}
