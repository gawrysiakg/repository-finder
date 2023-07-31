package com.repositoryfinder.finder.domain.model;

public class NotAcceptableResponseMediaTypeException extends RuntimeException{
    public NotAcceptableResponseMediaTypeException(String message) {
        super(message);
    }
}
