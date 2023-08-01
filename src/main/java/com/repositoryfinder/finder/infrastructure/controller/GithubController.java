package com.repositoryfinder.finder.infrastructure.controller;

import com.repositoryfinder.finder.GithubMapper;
import com.repositoryfinder.finder.infrastructure.error.BadMediaTypeException;
import com.repositoryfinder.finder.infrastructure.error.NotAcceptableResponseMediaTypeException;
import com.repositoryfinder.finder.domain.service.GithubService;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.GithubRequestDto;
import com.repositoryfinder.finder.infrastructure.error.ErrorUserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;

@RestController
public class GithubController {

    private final GithubService githubService;
    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GithubResponseDto>> getAllByUsername(@RequestBody @Valid GithubRequestDto githubRequestDto, @RequestHeader(name = ACCEPT) String accept) {
        isApplicationXmlAcceptHeader(accept);
        List<SingleRepository> allReposAndBranches = githubService.getAllReposWithBranches(githubRequestDto.username());
        return ResponseEntity.ok(GithubMapper.mapToGithubResponseDtoList(allReposAndBranches));
    }



    private static void isApplicationXmlAcceptHeader(String accept) {
        if (accept.equals(MediaType.APPLICATION_XML_VALUE)) {
            throw new NotAcceptableResponseMediaTypeException("xml is not acceptable media type, only application/json");
            //throw new BadMediaTypeException("xml is not acceptable media type, only application/json");
           // throw new HttpMediaTypeNotAcceptableException("xml is not acceptable media type, only application/json");
        }
    }



}
