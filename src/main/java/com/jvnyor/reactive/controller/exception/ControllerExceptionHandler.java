package com.jvnyor.reactive.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    Mono<ResponseEntity<ProblemDetail>> handleException(Exception exception) {

        var internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        var problemDetail = ErrorResponse.builder(exception, internalServerError, exception.getMessage()).build().getBody();

        int internalServerErrorValue = internalServerError.value();

        return Mono.just(ResponseEntity.status(internalServerErrorValue).body(problemDetail));
    }

    @ExceptionHandler(UserNotFoundException.class)
    Mono<ResponseEntity<ProblemDetail>> handleUserNotFoundException(UserNotFoundException exception) {

        var notFound = HttpStatus.NOT_FOUND;

        var problemDetail = ErrorResponse.builder(exception, notFound, exception.getMessage()).build().getBody();

        int notFoundValue = notFound.value();

        return Mono.just(ResponseEntity.status(notFoundValue).body(problemDetail));
    }
}
