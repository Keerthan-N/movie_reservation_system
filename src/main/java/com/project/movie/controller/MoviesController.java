package com.project.movie.controller;

import com.project.movie.dto.MovieDTO;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.model.Movies;
import com.project.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    private final MovieService service;

    @PostMapping("/add-movies")
    public CreatedSuccessfullyDTO addMovies(@RequestBody MovieDTO movieDTO){
        return service.addMovies(movieDTO);
    }

    @GetMapping("/get-movies")
    public List<Movies> getAllMovies(){
        return service.getAllMovies();
    }
}
