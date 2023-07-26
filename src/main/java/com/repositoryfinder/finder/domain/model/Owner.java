package com.repositoryfinder.finder.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Owner(String login) {
}
