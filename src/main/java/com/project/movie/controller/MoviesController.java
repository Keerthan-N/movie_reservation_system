package com.project.movie.controller;

import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.MoviesDTO;
import com.project.movie.service.MoviesService;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService service;

    @PostMapping("/add-movies")
    public ResponseEntity<MessageDTO> addMovies(@RequestBody MoviesDTO moviesDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addMovies(moviesDTO));
    }

    @GetMapping("/get-movies")
    public ResponseEntity<List<MoviesDTO>> getMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMovies());
    }

    @PutMapping("/update-movie")
    public ResponseEntity<MessageDTO> updateMovies(@RequestBody MoviesDTO moviesDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateMovie(moviesDTO));
    }

    @DeleteMapping("/delete-movie/{title}")
    public ResponseEntity<MessageDTO> deleteMovie(@PathVariable String title){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.deleteMovie(title));
    }
}
