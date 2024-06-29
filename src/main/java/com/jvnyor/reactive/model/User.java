package com.jvnyor.reactive.model;

import com.jvnyor.reactive.model.dto.UserResponseDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id @Nullable Long id,
        @NotBlank String username,
        @NotBlank String password,
        @Email String email
) {

    public UserResponseDTO toUserResponseDTO(@NotNull Long id, @NotBlank String username, @NotBlank String email) {
        return new UserResponseDTO(id, username, email);
    }
}
