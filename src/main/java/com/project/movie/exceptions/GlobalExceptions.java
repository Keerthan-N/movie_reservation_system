package com.project.movie.exceptions;

import com.project.movie.dto.MessageDTO;
import com.project.movie.exceptions.custom_exceptions.CancelError;
import com.project.movie.exceptions.custom_exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<MessageDTO> handleUserNotFound(UserNotFound userNotFound){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(HttpStatus.BAD_REQUEST, userNotFound.getMessage())
        );
    }

    @ExceptionHandler(CancelError.class)
    public ResponseEntity<MessageDTO> handleCancelError(CancelError cancelError){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(HttpStatus.BAD_REQUEST, cancelError.getMessage())
        );
    }
}
