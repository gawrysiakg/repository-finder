package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Branch;
import com.repositoryfinder.finder.domain.model.Owner;
import com.repositoryfinder.finder.domain.model.RepositoryProperty;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@Service
public class GithubService {


    private final GithubProxy githubClient;

    public GithubService(GithubProxy githubClient) {
        this.githubClient = githubClient;
    }

    public List<RepositoryProperty> getNotForkedRepositoryNamesForUser(String username){
        return githubClient.getAllReposForUser(username).stream()
                .filter(repositoryProperty -> !repositoryProperty.fork())
                .toList();
    }


    public List<SingleRepository> getAllReposWithBranches(String username){
        List<RepositoryProperty> repositoryNames = getNotForkedRepositoryNamesForUser(username);
        List<SingleRepository> list = new ArrayList<>();
        for(RepositoryProperty repository : repositoryNames){
            List<Branch> allBranchesForRepo = githubClient.getAllBranchesForRepo(username,  repository.name());
            list.add(new SingleRepository(repository.name(), new Owner(username), repository.fork(), allBranchesForRepo));
        }
        return list;
    }



}
