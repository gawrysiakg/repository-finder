package com.repositoryfinder.finder.infrastructure.error;

import com.repositoryfinder.finder.domain.model.NotAcceptableResponseMediaTypeException;
import com.repositoryfinder.finder.domain.model.NotExistingUserException;
import com.repositoryfinder.finder.domain.model.RepoNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Log4j2
@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class RepositoryFinderErrorHandler  {


    @ExceptionHandler(NotExistingUserException.class)
    ResponseEntity<ErrorUserResponseDto>handleNotExistingUserException(NotExistingUserException exception){
        log.warn("NotExistingUserException while Get request");
        return toResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage() );
    }



//    @ExceptionHandler(NotAcceptableResponseMediaTypeException.class)
//    ResponseEntity<ErrorUserResponseDto> handleNotAcceptableResponseMediaTypeException(NotAcceptableResponseMediaTypeException exception) {
//        log.warn("RepositoryFinderErrorHandler is handling NotAcceptableResponseMediaTypeException: "+exception.getMessage());
//        return toResponseEntity(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
//    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    ResponseEntity<ErrorUserResponseDto> handleNotAcceptableResponseMediaTypeException(HttpMediaTypeNotAcceptableException exception) {
        log.warn("RepositoryFinderErrorHandler is handling HttpMediaTypeNotAcceptableException: "+exception.getMessage());
        return toResponseEntity(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(RepoNotFoundException.class)
    ResponseEntity<ErrorUserResponseDto> handleRepoNotFoundException(RepoNotFoundException exception) {
        log.warn("RepositoryFinderErrorHandler is handling RepoNotFoundException: "+exception.getMessage());
        return toResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private static ResponseEntity<ErrorUserResponseDto> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ErrorUserResponseDto(status, message));
    }


}
