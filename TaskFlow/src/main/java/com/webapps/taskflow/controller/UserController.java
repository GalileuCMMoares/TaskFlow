package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.user.UserCreateRequest;
import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.dtos.user.UserUpdateRequest;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.UserMapper;
import com.webapps.taskflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> listAll(Pageable pageable){
        return userService.listAll(pageable);
    }

    @GetMapping("/me")
    public UserResponse me() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserMapper.toResponse(user);
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest request){
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
