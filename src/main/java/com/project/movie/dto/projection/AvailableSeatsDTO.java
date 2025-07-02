package com.project.movie.dto.projection;

import java.util.List;

public record AvailableSeatsDTO(int numberOfSeatsLeft , List<String> availableSeats) {
}
