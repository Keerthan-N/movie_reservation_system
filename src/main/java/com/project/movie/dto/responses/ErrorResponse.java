package com.project.movie.dto.responses;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status , int statusValue , String message) {
}
