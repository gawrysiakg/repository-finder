package com.repositoryfinder.finder.domain.repository;

import com.repositoryfinder.finder.domain.model.Repo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RepoRepository extends Repository<Repo, Long> {

    Repo save(Repo repo);

    //@Modifying nie trzeba tu bo ta operacja nie modyfikuje danych
    @Query("SELECT r FROM Repo r")
    List<Repo> findAll(Pageable pageable);

    @Query("SELECT r FROM Repo r WHERE r.id = :id")
    Optional<Repo> findRepoById(Long id);

    @Query("SELECT r FROM Repo r WHERE r.owner = :owner")
    List<Repo> findAllByOwner(Pageable pageable, String owner);

    @Modifying
    @Query("DELETE FROM Repo r WHERE r.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE Repo r SET r.owner = :#{#newRepo.owner}, r.name = :#{#newRepo.name} WHERE r.id = :id")
    void updateById(Long id, Repo newRepo);

    boolean existsById(Long id);

    boolean existsByOwnerAndName(String owner, String name);
}
