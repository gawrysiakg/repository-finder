package com.repositoryfinder.finder.infrastructure.error;


public class NotAcceptableResponseMediaTypeException extends RuntimeException{
    public NotAcceptableResponseMediaTypeException(String message) {
        super(message);
    }
}
