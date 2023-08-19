package com.repositoryfinder.finder.infrastructure.dto;

import org.springframework.http.HttpStatus;

public record DeleteRepoResponseDto(HttpStatus status, String message) {
}
