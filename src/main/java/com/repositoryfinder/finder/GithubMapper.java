package com.repositoryfinder.finder;

import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.infrastructure.dto.BranchDto;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;

import java.util.ArrayList;
import java.util.List;

public class GithubMapper {


    public static List<GithubResponseDto> mapToGithubResponseDtoList(List<SingleRepository> list) {
        List<GithubResponseDto> responseList = new ArrayList<>();
        return list.stream()
                .map(singleRepo -> new GithubResponseDto(
                        singleRepo.name(),
                        singleRepo.owner().login(),
                        singleRepo.branches()
                                .stream()
                                .map(branch -> new BranchDto(
                                        branch.name(),
                                        branch.commit().sha()))
                                .toList()))
                .toList();
    }
}
