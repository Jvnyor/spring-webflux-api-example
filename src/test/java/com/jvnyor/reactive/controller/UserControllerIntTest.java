package com.jvnyor.reactive.controller;

import com.jvnyor.reactive.model.User;
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
        final User user = new User(
                null,
                "user",
                "123",
                "user@gmail.com"
        );

        webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(postUser -> {
                    assertAll("User",
                            () -> assertNotNull(postUser.id()),
                            () -> assertEquals(user.username(), postUser.username()),
                            () -> assertEquals(user.password(), postUser.password()),
                            () -> assertEquals(user.email(), postUser.email()),
                            () -> assertNotEquals(user.id(), postUser.id())
                    );
                });
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