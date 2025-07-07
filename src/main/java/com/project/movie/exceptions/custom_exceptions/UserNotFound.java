package com.project.movie.exceptions.custom_exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound() {
    }

    public UserNotFound(String message) {
        super(message);
    }
}
