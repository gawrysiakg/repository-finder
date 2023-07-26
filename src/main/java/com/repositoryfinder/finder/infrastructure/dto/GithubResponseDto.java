package com.repositoryfinder.finder.infrastructure.dto;

import java.util.List;

public record GithubResponseDto(String repositoryName, String ownerLogin, List<BranchDto> branches) {
}
