package com.repositoryfinder.finder.infrastructure.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RepoRequestDto(
        @NotEmpty(message = "owner can not be empty")
        @NotNull(message = "owner can not be null")
        String owner,
        @NotEmpty(message = "name can not be empty")
        @NotNull(message = "name can not be null")
        String name) {
}
