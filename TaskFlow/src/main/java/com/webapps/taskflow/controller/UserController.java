package com.webapps.taskflow.controller;

import com.webapps.taskflow.dtos.user.UserCreateRequest;
import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.mapper.UserMapper;
import com.webapps.taskflow.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponse> listAll(){
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest request){
        User user = UserMapper.toEntity(request);
        return UserMapper.toResponse(userRepository.save(user));
    }
}
