package com.repositoryfinder.finder.domain.service;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.domain.model.RepoNotFoundException;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.domain.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    public Repo updateRepository(Long id, Repo repoFromRequest) {
        Optional<Repo> repoById = repository.findRepoById(id);
        if(repoById.isPresent()){
            Repo repoFromDb = repoById.get();
            repoFromDb.setName(repoFromRequest.getName());
            repoFromDb.setOwner(repoFromRequest.getOwner());
            return repoFromRequest;
        } else {
            log.info("RepoNotFoundException - Not found repository with id: "+id);
            throw new RepoNotFoundException("Not found repository with id: "+id);
        }
    }
}
