package com.project.movie.dto;

import org.springframework.http.HttpStatus;

public record MessageDTO(HttpStatus status , String message) {
}
