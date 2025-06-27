package com.project.movie.service;


import com.project.movie.dto.MovieDTO;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.model.Movies;
import com.project.movie.repository.MoviesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MoviesRepo moviesRepo;

    public CreatedSuccessfullyDTO addMovies(MovieDTO movieDTO){
        Movies movies = Movies.builder()
                .title(movieDTO.title())
                .releaseDate(movieDTO.releaseDate())
                .moviePoster(movieDTO.moviePoster())
                .genre(movieDTO.genre())
                .duration(movieDTO.duration())
                .language(movieDTO.language())
                .build();
        if(!ObjectUtils.isEmpty(movies)) moviesRepo.save(movies);
        return new CreatedSuccessfullyDTO(HttpStatus.OK.value(), movies);
    }

    public List<Movies> getAllMovies(){
        return moviesRepo.findAll();
    }

}
