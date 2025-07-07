package com.project.movie.exceptions.custom_exceptions;

public class CancelError extends RuntimeException {
    public CancelError(String message) {
        super(message);
    }
}
