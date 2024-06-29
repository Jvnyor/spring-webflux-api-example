package com.jvnyor.reactive.controller;

import com.jvnyor.reactive.model.User;
import com.jvnyor.reactive.model.dto.UserRequestDTO;
import com.jvnyor.reactive.model.dto.UserResponseDTO;
import com.jvnyor.reactive.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUserToCreate_whenCreateUser_thenReturnUserCreated() {
        final UserRequestDTO userRequestDTO = new UserRequestDTO(
                "user",
                "123",
                "user@gmail.com"
        );

        webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(userRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .value(postUser -> {
                    assertAll("User response",
                            () -> assertNotNull(postUser.id()),
                            () -> assertEquals(userRequestDTO.username(), postUser.username()),
                            () -> assertNotNull(userRequestDTO.password()),
                            () -> assertEquals(userRequestDTO.email(), postUser.email()),
                            () -> assertNotNull(postUser.id())
                    );
                });
    }

    @Test
    void givenUserToCreateWithInvalidEmail_whenCreateUser_thenExceptionIsThrown() {
        final User user = new User(
                null,
                "user",
                "123",
                "user"
        );

        webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenUsersRegistered_whenGetUsers_thenReturnAllUsersRegistered() {
        final User user = userRepository.save(
                new User(
                null,
                "user",
                "123",
                "user@gmail.com"
                )
        ).block();

        webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> {

                    var userResponse = users.get(0);

                    assertAll("User",
                            () -> assertNotNull(userResponse.id()),
                            () -> assertEquals(user.id(), userResponse.id()),
                            () -> assertEquals(user.username(), userResponse.username()),
                            () -> assertEquals(user.password(), userResponse.password()),
                            () -> assertEquals(user.email(), userResponse.email())
                    );
                });
    }
}