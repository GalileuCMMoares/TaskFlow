package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.user.UserCreateRequest;
import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.UserMapper;
import com.webapps.taskflow.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<UserResponse> listAll(Pageable pageable){
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest request){
        User user = UserMapper.toEntity(request);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
        user.softDelete();
        userRepository.save(user);
    }
}
