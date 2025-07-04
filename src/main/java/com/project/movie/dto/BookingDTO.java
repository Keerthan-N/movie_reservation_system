package com.project.movie.dto;

import java.time.LocalDate;
import java.util.List;

public record BookingDTO(
        String movieTitle ,
        String showTime ,
        LocalDate date,
        List<String> seatNumbers,
        double price) {
}
