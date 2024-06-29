package com.jvnyor.reactive.controller;

import com.jvnyor.reactive.controller.exception.UserNotFoundException;
import com.jvnyor.reactive.model.User;
import com.jvnyor.reactive.model.dto.UserRequestDTO;
import com.jvnyor.reactive.model.dto.UserResponseDTO;
import com.jvnyor.reactive.repository.UserRepository;
import jakarta.validation.Valid;
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
    public Mono<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userRepository.save(userRequestDTO.toUser(userRequestDTO.email(), userRequestDTO.password(), userRequestDTO.email()))
                .map(user -> user.toUserResponseDTO(user.id(), user.username(), user.email()));
    }

    @PutMapping("/{id}")
    public Mono<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(existingUser -> userRepository.save(new User(existingUser.id(), userRequestDTO.username(), userRequestDTO.password(), userRequestDTO.email()))
                        .map(user -> user.toUserResponseDTO(user.id(), user.username(), user.email())));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userRepository.deleteById(id);
    }

    @GetMapping
    public Flux<UserResponseDTO> getUsers() {
        return userRepository.findAll().map(user -> user.toUserResponseDTO(user.id(), user.username(), user.email()));
    }
}
