package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.*;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@Service
@Slf4j
public class GithubService {


    private final GithubProxy githubClient;

    public GithubService(GithubProxy githubClient) {
        this.githubClient = githubClient;
    }

    public List<RepositoryProperty> getNotForkedRepositoryNamesForUser(String username) {
        try {
            return githubClient.getAllReposForUser(username).stream()
                    .filter(repositoryProperty -> !repositoryProperty.fork())
                    .toList();
        } catch (FeignException.FeignClientException exception) {
            log.error("Feign client exception " + exception.status());
            throw new NotExistingUserException("Resources not found for this user, probably bad username");
        } catch (FeignException.FeignServerException serverException) {
            log.error("Feign server exception " + serverException.getMessage() + " " + serverException.status());
        } catch (RetryableException retryableException) {
            log.error("Retryable exception " + retryableException.getMessage() + " " + retryableException.status());
        } catch (FeignException feignException) {
            log.error("Feign exception " + feignException.getMessage() + " " + feignException.status());
        }
        return new ArrayList<>();
    }


    public List<SingleRepository> getAllReposWithBranches(String username) {

        List<RepositoryProperty> repositoryNames = getNotForkedRepositoryNamesForUser(username);
        List<SingleRepository> list = new ArrayList<>();
        try {
            for (RepositoryProperty repository : repositoryNames) {
                List<Branch> allBranchesForRepo = githubClient.getAllBranchesForRepo(username, repository.name());
                list.add(new SingleRepository(repository.name(), new Owner(username), repository.fork(), allBranchesForRepo));
            }

        } catch (FeignException.FeignServerException serverException) {
            log.error("Feign server exception " + serverException.getMessage() + " " + serverException.status());
        } catch (RetryableException retryableException) {
            log.error("Retryable exception " + retryableException.getMessage() + " " + retryableException.status());
        } catch (FeignException feignException) {
            log.error("Feign exception " + feignException.getMessage() + " " + feignException.status());
        }

        return list;
    }


}
