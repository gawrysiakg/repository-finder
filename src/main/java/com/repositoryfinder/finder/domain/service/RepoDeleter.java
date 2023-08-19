package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.RepoNotFoundException;
import com.repositoryfinder.finder.domain.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepoDeleter {

    private final RepoRepository repository;

    public RepoDeleter(RepoRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long id){
        existsById(id);
        repository.deleteById(id);
    }



    public void existsById(Long id) {
        if (!repository.existsById(id))
            throw new RepoNotFoundException("Repository with id " + id + " not exist");
    }

}
