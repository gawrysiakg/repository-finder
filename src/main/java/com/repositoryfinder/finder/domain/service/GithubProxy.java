package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Branch;
import com.repositoryfinder.finder.domain.model.RepositoryProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "github-client")
public interface GithubProxy {

    @GetMapping("/users/{username}/repos")
    List<RepositoryProperty> getAllReposForUser(@PathVariable String username);


    @GetMapping("/repos/{username}/{repo}/branches")
    List<Branch> getAllBranchesForRepo(@PathVariable String username, @PathVariable String repo);
}
