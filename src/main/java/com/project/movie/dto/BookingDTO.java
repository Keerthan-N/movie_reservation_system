package com.project.movie.dto;

import java.util.List;

public record BookingDTO(String movieTitle , String showTime , List<String> seatNumbers) {
}
