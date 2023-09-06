package com.repositoryfinder.finder.domain.model;

public class CustomFeignException extends RuntimeException {
    public CustomFeignException(String message)  {
        super(message);
    }
}
