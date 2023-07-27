package com.repositoryfinder.finder.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GithubRequestDto(
        @NotEmpty(message = "Username can not be empty")
        @NotNull(message = "Username can not be null")
        String username) {
}
