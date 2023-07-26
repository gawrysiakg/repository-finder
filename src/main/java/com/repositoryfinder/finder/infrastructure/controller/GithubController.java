package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.GithubMapper;
import com.repositoryfinder.finder.domain.service.GithubService;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.GithubRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {

    GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(consumes = "application/json")
        // @Headers("Accept: application/json")
    ResponseEntity<List<GithubResponseDto>> getAllByUsername(@RequestBody GithubRequestDto githubRequestDto) {
        List<SingleRepository> allReposAndBranches = githubService.getAllReposWithBranches(githubRequestDto.username());
        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposAndBranches));
    }

}
