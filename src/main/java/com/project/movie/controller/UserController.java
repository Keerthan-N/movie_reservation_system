package com.project.movie.controller;

import com.project.movie.dto.BookingDTO;
import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.UserDTO;
import com.project.movie.dto.UsersMoviesDTO;
import com.project.movie.dto.projection.BookingConfrimedDTO;
import com.project.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @PostMapping("/register-user")
    public ResponseEntity<MessageDTO> register(@RequestBody UserDTO dto){
        return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<MessageDTO> login(@PathVariable String username , @PathVariable String password){
        return ResponseEntity.ok(service.login(username,password));
    }

    @GetMapping("/get-movies")
    public ResponseEntity<List<UsersMoviesDTO>> getMovies(){
        return ResponseEntity.ok(service.getMoviesList());
    }

    @PostMapping("/book-tickets/{username}")
    public ResponseEntity<BookingConfrimedDTO> bookTickets(@PathVariable String username , @RequestBody BookingDTO dto){
        return ResponseEntity.ok(service.bookTickets(username,dto));
    }

    @DeleteMapping("/cancel-tickets/{username}/{movieTitle}")
    public ResponseEntity<MessageDTO> cancelTickets(@PathVariable String username , @PathVariable String movieTitle){
        return ResponseEntity.ok(service.cancelTickets(username,movieTitle));
    }
}
