package com.project.movie.dto;

import java.util.List;

public record MoviesDTO(String title,
                        String description ,
                        String posterImage ,
                        String genre ,
                        List<String> showTimings) {
}
