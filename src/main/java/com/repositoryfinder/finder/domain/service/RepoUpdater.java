package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.model.RepoNotFoundException;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.domain.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepoUpdater {


    private final RepoRepository repository;

    public RepoUpdater(RepoRepository repository) {
        this.repository = repository;
    }

    public void updateToDatabaseIfNotExist(SingleRepository singleRepository) {
        if (!repository.existsByOwnerAndName(singleRepository.owner().login(), singleRepository.name())) {
            repository.save(new Repo(singleRepository.owner().login(), singleRepository.name()));
        }
    }


    public void existsById(Long id) {
        if (!repository.existsById(id))
            throw new RepoNotFoundException("Repository with id " + id + " not exist");
    }


}
