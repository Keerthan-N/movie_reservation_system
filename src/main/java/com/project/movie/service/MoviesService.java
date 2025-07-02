package com.project.movie.service;

import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.MoviesDTO;
import com.project.movie.exceptions.UserNotFound;
import com.project.movie.model.Movies;
import com.project.movie.model.Seats;
import com.project.movie.repository.MoviesRepo;
import com.project.movie.repository.SeatsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MoviesRepo repo;
    private final SeatsRepo seatsRepo;

    public MessageDTO generateSeats(){
        List<String> seatsList = new ArrayList<>();
        for(char letter = 'A'; letter <= 'Z';letter++){
            for (int number = 1; number<= 20; number++){
                String seats = String.format("%s-%d",letter,number);
                seatsList.add(seats);
            }
        }
        List<Seats> seats = seatsList.stream().map(seat -> Seats.builder().seatNumbers(seat).build()).toList();
        seatsRepo.saveAll(seats);
        return new MessageDTO(HttpStatus.CREATED,"Seats generated");
    }

    public MessageDTO addMovies(MoviesDTO dto) {
        if(!ObjectUtils.isEmpty(dto)){
            Movies movies = Movies.builder()
                    .title(dto.title())
                    .description(dto.description())
                    .posterImage(dto.posterImage())
                    .genre(dto.genre())
                    .showTimings(dto.showTimings())
                    .build();
            repo.save(movies);
            return new MessageDTO(HttpStatus.CREATED, dto.title()+" movie details added");
        }
        return new MessageDTO(HttpStatus.BAD_REQUEST,"Movie details cannot be empty");
    }

    public List<MoviesDTO> getMovies(){
        List<MoviesDTO> movies = repo.findAll().stream().map(
                data -> new MoviesDTO(data.getTitle(), data.getDescription(), data.getPosterImage(), data.getGenre(), data.getShowTimings())
        ).toList();
        return movies;
    }

    public Movies findByMovieTitle(String title){
        return Optional.ofNullable(repo.findByTitle(title)).get()
                .orElseThrow(() -> new UserNotFound("Movie not found"));
    }

    public MessageDTO   updateMovie(MoviesDTO moviesDTO){
        Movies movies = repo.findByTitle(moviesDTO.title())
                .orElseThrow(() -> new UserNotFound("Movie not found"));
        if(!ObjectUtils.isEmpty(movies)) {
            if (moviesDTO.title() != null || moviesDTO.title() != "") {
                movies.setTitle(moviesDTO.title());
            }
            if (moviesDTO.description() != null || moviesDTO.description() != "") {
                movies.setDescription(moviesDTO.description());
            }
            if (moviesDTO.posterImage() != null || moviesDTO.posterImage() != "") {
                movies.setPosterImage(moviesDTO.posterImage());
            }
            if (moviesDTO.genre() != null || moviesDTO.genre() != "") {
                movies.setGenre(movies.getGenre());
            }
            if (moviesDTO.showTimings() != null || !moviesDTO.showTimings().isEmpty()) {
                movies.setShowTimings(moviesDTO.showTimings());
            }
            repo.save(movies);
        }
        return new MessageDTO(HttpStatus.ACCEPTED,moviesDTO.title()+" has been updated");
    }

    public MessageDTO deleteMovie(String title){
        repo.deleteByTitle(title);
        return new MessageDTO(HttpStatus.ACCEPTED,title+" has been removed");
    }
}
