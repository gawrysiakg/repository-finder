package com.repositoryfinder.finder.infrastructure.dto.mapper;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.infrastructure.dto.DeleteRepoResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.RepoRequestDto;
import com.repositoryfinder.finder.infrastructure.dto.RepoResponseDto;
import com.repositoryfinder.finder.infrastructure.dto.UpdateRepoRequestDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class RepoMapper {



    public static RepoResponseDto mapFromRepoToRepoResponseDto(Repo repo){
        return new RepoResponseDto(repo.getId(), repo.getOwner(), repo.getName());
    }

    public static List<RepoResponseDto> mapToListRepoResponseDto(List<Repo> list){
        return list.stream().map(RepoMapper::mapFromRepoToRepoResponseDto).collect(Collectors.toList());
    }

    public static Repo mapFromRepoRequestDtoToRepo(RepoRequestDto repoRequestDto){
        return new Repo( repoRequestDto.owner(), repoRequestDto.name());
    }

    public static DeleteRepoResponseDto mapToDeleteRepoResponseDto(Long id){
        return new DeleteRepoResponseDto(HttpStatus.OK, "Deleted repository with id: "+id);
    }

    public static Repo mapFromUpdateRepoRequestDtoToRepo(Long id, UpdateRepoRequestDto updateRepoRequestDto){
        return new Repo(id, updateRepoRequestDto.owner(), updateRepoRequestDto.name());
    }
}
