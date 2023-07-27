package com.repositoryfinder.finder.domain.model;

public class NotAcceptableResponseMediaType extends RuntimeException{
    public NotAcceptableResponseMediaType(String message) {
        super(message);
    }
}
