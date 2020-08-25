package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.commandsdata.users.UserPatchCommandData;
import com.github.haseoo.courier.services.ports.UserService;
import com.github.haseoo.courier.views.users.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

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
    @PatchMapping("{id}")
    public ResponseEntity<UserView> patchActivation(@PathVariable Long id, @RequestBody UserPatchCommandData active) {
        if (active.isActive()) {
            return new ResponseEntity<>(UserView.of(userService.setAsActive(id)), OK);
        }
        return new ResponseEntity<>(UserView.of(userService.setAsInactive(id)), OK);
    }
}
