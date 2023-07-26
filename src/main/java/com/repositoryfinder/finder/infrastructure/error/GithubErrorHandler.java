package com.repositoryfinder.finder.infrastructure.error;

import com.repositoryfinder.finder.domain.model.NotExistingUserException;
import com.repositoryfinder.finder.infrastructure.controller.GithubController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = GithubController.class)
public class GithubErrorHandler {


    @ExceptionHandler(NotExistingUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorUserResponseDto handleException(NotExistingUserException exception){
        log.warn("NotExistingUserException while Get request");
        return new ErrorUserResponseDto(HttpStatus.NOT_FOUND, exception.getMessage() ); //message już podawaliśmy w throw
    }


}
