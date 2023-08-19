package com.repositoryfinder.finder.infrastructure.mapper;

import com.repositoryfinder.finder.domain.model.Repo;
import com.repositoryfinder.finder.infrastructure.dto.RepoRequestDto;
import com.repositoryfinder.finder.infrastructure.dto.RepoResponseDto;

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
}
