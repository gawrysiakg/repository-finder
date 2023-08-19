package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.infrastructure.mapper.GithubMapper;
import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.service.RepoAdder;
import com.repositoryfinder.finder.domain.service.RepoDeleter;
import com.repositoryfinder.finder.domain.service.RepoRetriever;
import com.repositoryfinder.finder.domain.service.RepoUpdater;
import com.repositoryfinder.finder.infrastructure.dto.GithubRequestDto;
import com.repositoryfinder.finder.infrastructure.dto.RepoResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepoController {

        private final RepoAdder repoAdder;
        private final RepoDeleter repoDeleter;
        private final RepoUpdater repoUpdater;
        private final RepoRetriever repoRetriever;

    public RepoController(RepoAdder repoAdder, RepoDeleter repoDeleter, RepoUpdater repoUpdater, RepoRetriever repoRetriever) {
        this.repoAdder = repoAdder;
        this.repoDeleter = repoDeleter;
        this.repoUpdater = repoUpdater;
        this.repoRetriever = repoRetriever;
    }


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RepoResponseDto>> getAllByUsername(Pageable pageable, @RequestBody @Valid GithubRequestDto githubRequestDto, @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new HttpMediaTypeNotAcceptableException("%s is unsupported media type".formatted(acceptHeader));
        }
        List<Repo> allReposForUser = repoRetriever.getAllReposForUser(pageable, githubRequestDto.username());
        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposForUser));
    }






}
