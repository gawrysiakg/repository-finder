package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Branch;
import com.repositoryfinder.finder.domain.model.Owner;
import com.repositoryfinder.finder.domain.model.RepositoryProperty;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

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

    public List<RepositoryProperty> getNotForkedRepositoryNamesForUser(String username){
       // try{
            return githubClient.getAllReposForUser(username).stream()
                    .filter(repositoryProperty -> !repositoryProperty.fork())
                    .toList();
//        }  catch (
//    FeignException.FeignClientException exception) {
//        log.error("Feign client exception " + exception.status()); //getMessage print body message
//    } catch (FeignException.FeignServerException serverException) {
//        log.error("Feign server exception " + serverException.getMessage() + " " + serverException.status());
//    } catch (
//    RetryableException retryableException) {
//        log.error("Retryable exception " + retryableException.getMessage() + " " + retryableException.status());
//    } catch (FeignException feignException) {
//        log.error("Feign exception " + feignException.getMessage() + " " + feignException.status());
//    }
//        return new ArrayList<>();
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
