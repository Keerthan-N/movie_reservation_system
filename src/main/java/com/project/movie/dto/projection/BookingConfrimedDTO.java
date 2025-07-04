package com.project.movie.dto.projection;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

public record BookingConfrimedDTO(
        String message ,
        String movieTitle,
        int numberOfSeats ,
        List<String> seats,
        String showTime,
        LocalDate date
) {
}
