package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.domain.model.RepoNotFoundException;
import com.repositoryfinder.finder.infrastructure.dto.*;
import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.service.RepoAdder;
import com.repositoryfinder.finder.domain.service.RepoDeleter;
import com.repositoryfinder.finder.domain.service.RepoRetriever;
import com.repositoryfinder.finder.domain.service.RepoUpdater;
import com.repositoryfinder.finder.infrastructure.mapper.RepoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db")
@RequiredArgsConstructor
public class RepoController {

    private final RepoAdder repoAdder;
    private final RepoDeleter repoDeleter;
    private final RepoUpdater repoUpdater;
    private final RepoRetriever repoRetriever;


    @GetMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RepoResponseDto>> getUserRepositories(Pageable pageable, @RequestBody @Valid GithubRequestDto githubRequestDto, @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        validateHeader(acceptHeader);
        List<Repo> allReposForUser = repoRetriever.getAllReposForUser(pageable, githubRequestDto.username());
        return ResponseEntity.ok(RepoMapper.mapToListRepoResponseDto(allReposForUser));
    }


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<RepoResponseDto>> getAllRepositories(Pageable pageable, @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        validateHeader(acceptHeader);
        List<Repo> allRepos = repoRetriever.retrieveAllRepoFromDb(pageable);
        return ResponseEntity.ok(RepoMapper.mapToListRepoResponseDto(allRepos));
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RepoResponseDto> getRepositoryById(@PathVariable Long id, @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        validateHeader(acceptHeader);
        Optional<Repo> repo = repoRetriever.findById(id);
        return ResponseEntity.ok(RepoMapper.mapFromRepoToRepoResponseDto(repo
                .orElseThrow(() -> new RepoNotFoundException("Not found repo with id: " + id))));
    }

    private static void validateHeader(MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new HttpMediaTypeNotAcceptableException("%s is unsupported media type".formatted(acceptHeader));
        }
    }

    @PostMapping
    ResponseEntity<RepoResponseDto> addRepositoryToDb(@RequestBody @Valid RepoRequestDto repoRequestDto){
        Repo repoFromRequest = RepoMapper.mapFromRepoRequestDtoToRepo(repoRequestDto);
        repoAdder.addRepo(repoFromRequest);
        return ResponseEntity.ok(RepoMapper.mapFromRepoToRepoResponseDto(repoFromRequest));
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<DeleteRepoResponseDto> deleteRepositoryFromDb(@PathVariable Long id){
        repoDeleter.deleteById(id);
        return ResponseEntity.ok(RepoMapper.mapToDeleteRepoResponseDto(id));
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<RepoResponseDto> updateRepository(@PathVariable Long id, @RequestBody @Valid UpdateRepoRequestDto updateRepoRequestDto){
        Repo repoFromRequest = RepoMapper.mapFromUpdateRepoRequestDtoToRepo(id, updateRepoRequestDto);

        Repo updatedRepo = repoUpdater.updateRepository(id, repoFromRequest);
        return ResponseEntity.ok(RepoMapper.mapFromRepoToRepoResponseDto(updatedRepo));
    }

    @PatchMapping(value = "/{id}")
    ResponseEntity<RepoResponseDto> partiallyUpdateRepository(@PathVariable Long id, @RequestBody UpdateRepoRequestDto updateRepoRequestDto){
        Repo repoFromRequest = RepoMapper.mapFromUpdateRepoRequestDtoToRepo(id, updateRepoRequestDto);
        Repo updatedRepo = repoUpdater.partiallyUpdateRepository(id, repoFromRequest);
        return ResponseEntity.ok(RepoMapper.mapFromRepoToRepoResponseDto(updatedRepo));
    }


}
