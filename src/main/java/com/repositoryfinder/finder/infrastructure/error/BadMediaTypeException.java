package com.repositoryfinder.finder.infrastructure.error;

import java.io.IOException;

public class BadMediaTypeException extends IOException {
    public BadMediaTypeException(String message) {
        super(message);
    }
}
