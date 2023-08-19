package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.infrastructure.mapper.GithubMapper;
import com.repositoryfinder.finder.domain.model.NotAcceptableResponseMediaTypeException;
import com.repositoryfinder.finder.domain.service.GithubService;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.GithubRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GithubController {

    private final GithubService githubService;


    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }



    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GithubResponseDto>> getAllByUsername(@RequestBody @Valid GithubRequestDto githubRequestDto, @RequestHeader(HttpHeaders.ACCEPT) MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (acceptHeader.equals(MediaType.APPLICATION_XML)) {
            throw new HttpMediaTypeNotAcceptableException("%s is unsupported media type".formatted(acceptHeader));
        }
        List<SingleRepository> allReposAndBranches = githubService.getAllReposWithBranches(githubRequestDto.username());
        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposAndBranches));
    }


//    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<List<GithubResponseDto>> getAllByUsername(@RequestBody @Valid GithubRequestDto githubRequestDto, @RequestHeader(name = ACCEPT) String accept) {
//        isApplicationXmlAcceptHeader(accept);
//        List<SingleRepository> allReposAndBranches = githubService.getAllReposWithBranches(githubRequestDto.username());
//        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposAndBranches));
//    }


    private static void isApplicationXmlAcceptHeader(String accept) {
        if (accept.equals(MediaType.APPLICATION_XML_VALUE)) {
            throw new NotAcceptableResponseMediaTypeException("xml is not acceptable media type, only application/json");
        }
    }


}
