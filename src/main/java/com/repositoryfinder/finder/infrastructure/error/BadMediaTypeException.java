package com.repositoryfinder.finder.infrastructure.error;

public class BadMediaTypeException extends RuntimeException{
    public BadMediaTypeException(String message) {
        super(message);
    }
}
