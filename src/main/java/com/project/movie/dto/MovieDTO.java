package com.project.movie.dto;

import java.time.LocalDate;

public record MovieDTO(String title,
                       LocalDate releaseDate,
                       int duration,
                       String moviePoster,
                       String genre,
                       String language) {
}
