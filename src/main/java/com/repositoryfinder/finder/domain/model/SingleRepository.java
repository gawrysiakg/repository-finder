package com.repositoryfinder.finder.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record SingleRepository(
        String name,
        Owner owner,
        boolean fork,
        List<Branch> branches
) {
}
