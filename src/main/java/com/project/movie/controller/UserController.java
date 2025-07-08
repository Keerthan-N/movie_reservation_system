package com.project.movie.controller;

import com.project.movie.dto.BookingDTO;
import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.UserDTO;
import com.project.movie.dto.UsersMoviesDTO;
import com.project.movie.dto.projection.AvailableSeatsDTO;
import com.project.movie.dto.projection.BookingConfrimedDTO;
import com.project.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
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

    @GetMapping("/get-available-seats")
    public ResponseEntity<AvailableSeatsDTO> availableSeats(){
       return ResponseEntity.ok(service.availableSeats());
    }

    @SneakyThrows
    @GetMapping("/download-ticket/{userId}/{movieId}")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable String userId , @PathVariable String movieId){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        service.downloadTicket(byteArrayOutputStream,userId,movieId);
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=movie_ticket.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
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
