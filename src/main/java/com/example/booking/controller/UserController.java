package com.example.booking.controller;

import com.example.booking.dto.user.UserRq;
import com.example.booking.dto.user.UserRs;
import com.example.booking.entity.RoleType;
import com.example.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserRs findById(@PathVariable Long id) {
        return userService.findByIdRs(id);
    }

    @PostMapping("/create")
    public UserRs create(@RequestParam RoleType roleType, @RequestBody @Valid UserRq request) {
        log.info("UserController -> create roleType={} request={}", roleType, request);
        return userService.save(request, roleType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserRs update(@PathVariable Long id, @RequestBody  @Valid UserRq request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
