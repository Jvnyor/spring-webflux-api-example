package com.jvnyor.reactive.controller;

import com.jvnyor.reactive.model.User;
import com.jvnyor.reactive.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Flux<User> getUsers() {
        return userRepository.findAll();
    }
}
