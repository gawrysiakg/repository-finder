package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RepoRetriever {

    private final RepoRepository repository;

    public RepoRetriever(RepoRepository repository) {
        this.repository = repository;
    }

    public List<Repo> retrieveAllRepoFromDb(Pageable pageable){
        return repository.findAll(pageable);
    }

    public List<Repo> getAllReposForUser(Pageable pageable, String username){
        return repository.findAllByOwner(pageable, username);
    }

    public Optional<Repo> findById(Long id){
        return repository.findRepoById(id);
    }


}
