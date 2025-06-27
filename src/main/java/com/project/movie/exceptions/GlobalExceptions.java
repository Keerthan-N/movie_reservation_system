package com.project.movie.exceptions;

import com.project.movie.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse userNotFoundException(UserNotFoundException exception){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),exception.getMessage());
    }

}
