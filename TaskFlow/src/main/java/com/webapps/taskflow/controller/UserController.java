package com.webapps.taskflow.controller;

import com.webapps.taskflow.entity.User;
import com.webapps.taskflow.repository.UserRepository;
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
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }
}
