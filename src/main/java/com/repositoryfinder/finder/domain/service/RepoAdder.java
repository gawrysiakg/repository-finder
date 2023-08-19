package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepoAdder {

    private final RepoRepository repository;

    public RepoAdder(RepoRepository repository) {
        this.repository = repository;
    }

    public Repo addRepo(Repo repo){
        return repository.save(repo);
    }

}
