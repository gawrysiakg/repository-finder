package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.GithubMapper;
import com.repositoryfinder.finder.domain.service.GithubService;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.GithubRequestDto;
import feign.Headers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping (headers="Accept=application/json", consumes="application/json", produces="application/json")
    ResponseEntity<List<GithubResponseDto>> getAllByUsername(@RequestBody GithubRequestDto githubRequestDto) {
        List<SingleRepository> allReposAndBranches = githubService.getAllReposWithBranches(githubRequestDto.username());
        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposAndBranches));
    }
//Kluczowe jest, że "Content-Type" odnosi się do typu zawartości przesyłanej w ciele żądania,
// a "Accept" określa preferowany typ mediów w odpowiedzi serwera, który klient chciałby otrzymać.
}
