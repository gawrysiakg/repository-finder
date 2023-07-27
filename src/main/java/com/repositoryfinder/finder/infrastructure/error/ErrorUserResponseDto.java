package com.repositoryfinder.finder.infrastructure.error;

import org.springframework.http.HttpStatus;

public record ErrorUserResponseDto(HttpStatus status, String Message) {
}
