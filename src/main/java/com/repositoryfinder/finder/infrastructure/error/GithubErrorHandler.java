package com.repositoryfinder.finder.infrastructure.error;

import com.repositoryfinder.finder.domain.model.NotAcceptableResponseMediaType;
import com.repositoryfinder.finder.domain.model.NotExistingUserException;
import com.repositoryfinder.finder.infrastructure.controller.GithubController;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice(assignableTypes = GithubController.class)
//@Order(Ordered.HIGHEST_PRECEDENCE) // Ustaw priorytet na najwyższy
public class GithubErrorHandler {


    @ExceptionHandler(NotExistingUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorUserResponseDto handleException(NotExistingUserException exception){
        log.warn("NotExistingUserException while Get request");
        return new ErrorUserResponseDto(HttpStatus.NOT_FOUND, exception.getMessage() ); //message już podawaliśmy w throw
    }

    @ExceptionHandler(BadMediaTypeException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    ErrorUserResponseDto handleNotAcceptableException(BadMediaTypeException exception) {
       // log.warn("Not acceptable media type, only application/json");
        return new ErrorUserResponseDto(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }
        //return new ErrorUserResponseDto(HttpStatus.NOT_ACCEPTABLE, "Not acceptable media type, only application/json");
   // }



}
