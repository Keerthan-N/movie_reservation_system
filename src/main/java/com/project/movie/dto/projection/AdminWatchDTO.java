package com.project.movie.dto.projection;

import java.time.LocalDate;
import java.util.List;

public record AdminWatchDTO(
        Long bookingId,
        String movieTitle,
        List<String> seatNumber,
        String showtime,
        String userName,
        LocalDate bookingDate,
        double price
) {
}
