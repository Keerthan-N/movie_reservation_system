package com.project.movie.dto.projection;

import org.springframework.http.HttpStatus;

import java.util.List;

public record BookingConfrimedDTO(
        HttpStatus status,
        String message ,
        String movieTitle,
        int numberOfSeats ,
        List<String> seats,
        String showTime
) {
}
