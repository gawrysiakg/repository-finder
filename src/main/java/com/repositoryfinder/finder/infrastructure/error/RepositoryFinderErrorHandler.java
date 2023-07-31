package com.repositoryfinder.finder.infrastructure.error;

import com.repositoryfinder.finder.domain.model.NotAcceptableResponseMediaTypeException;
import com.repositoryfinder.finder.domain.model.NotExistingUserException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RepositoryFinderErrorHandler  {


    @ExceptionHandler(NotExistingUserException.class)
    ResponseEntity<ErrorUserResponseDto>handleNotExistingUserException(NotExistingUserException exception){
        log.warn("NotExistingUserException while Get request");
        return toResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage() );
    }


    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    ResponseEntity<ErrorUserResponseDto> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
        return toResponseEntity(exception.getStatusCode(), "Only Accept : Application/json is correct.");
    }


    @ExceptionHandler(NotAcceptableResponseMediaTypeException.class)
    ResponseEntity<ErrorUserResponseDto> handleNotAcceptableResponseMediaTypeException(NotAcceptableResponseMediaTypeException exception) {
        log.warn("RepositoryFinderErrorHandler is handling NotAcceptableResponseMediaType: "+exception.getMessage());
        return toResponseEntity(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    private static ResponseEntity<ErrorUserResponseDto> toResponseEntity(HttpStatusCode exception, String message) {
        return ResponseEntity.status(exception).body(new ErrorUserResponseDto(HttpStatus.NOT_ACCEPTABLE, message));
    }

}
