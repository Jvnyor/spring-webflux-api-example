package com.jvnyor.reactive.model.dto;

import com.jvnyor.reactive.model.User;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email) {

    public User toUser(String username, String password, String email) {
        return new User(null, username, password, email);
    }
}
