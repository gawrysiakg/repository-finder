package com.repositoryfinder.finder.domain.model;

public class NotExistingUserException extends RuntimeException{

    public NotExistingUserException(String message) {
        super(message);
    }
}
