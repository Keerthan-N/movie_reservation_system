package com.project.movie.service;

import com.project.movie.dto.*;
import com.project.movie.dto.projection.AvailableSeatsDTO;
import com.project.movie.dto.projection.BookingConfrimedDTO;
import com.project.movie.exceptions.UserNotFound;
import com.project.movie.model.Booking;
import com.project.movie.model.Movies;
import com.project.movie.model.Seats;
import com.project.movie.model.Users;
import com.project.movie.repository.BookingRepo;
import com.project.movie.repository.MoviesRepo;
import com.project.movie.repository.SeatsRepo;
import com.project.movie.repository.UserRepo;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final MoviesRepo moviesRepo;
    private final SeatsRepo seatsRepo;
    private final BookingRepo bookingRepo;

    public MessageDTO register(UserDTO dto){
        if(!ObjectUtils.isEmpty(dto)){
            Users user = Users.builder()
                    .username(dto.username())
                    .password(dto.password())
                    .displayName(dto.displayName())
                    .roles(dto.role())
                    .build();
            repo.save(user);
        }
        return new MessageDTO(HttpStatus.CREATED, dto.displayName()+" has been added");
    }

    public MessageDTO login(String username , String password){
        if(StringUtils.hasLength(username) && StringUtils.hasLength(password)){
            Optional.ofNullable(repo.findByUsernameAndPassword(username,password))
                    .orElseThrow(()-> new UserNotFound("User Not found"));
            return new MessageDTO(HttpStatus.OK,"Login Successfull");
        }
        return new MessageDTO(HttpStatus.BAD_REQUEST,"Username / Password cannot be empty");
    }

    public List<UsersMoviesDTO> getMoviesList(){
        List<UsersMoviesDTO> moviesList = moviesRepo.findAll().stream().map(
                movie -> new UsersMoviesDTO(movie.getTitle(), movie.getShowTimings())
        ).toList();
        return moviesList;
    }

    public BookingConfrimedDTO bookTickets(String username , BookingDTO bookingDTO){
        Long userId = repo.findByUsername(username)
                .map(Users::getId)
                .orElseThrow(()-> new UserNotFound("User not found"));
        Long movieId = moviesRepo.findByTitle(bookingDTO.movieTitle())
                .map(Movies::getId)
                .orElseThrow(()-> new UserNotFound("Movie not found"));
        List<Long> seatIds = seatsRepo.findBySeatNumbers(bookingDTO.seatNumbers());
        Booking booking = Booking.builder()
                .userId(userId)
                .movieId(movieId)
                .showTime(bookingDTO.showTime())
                .seatId(seatIds).build();
        bookingRepo.save(booking);
        return new BookingConfrimedDTO(HttpStatus.CREATED,
                "Bookig Confrimed",
                bookingDTO.movieTitle()
                ,seatIds.size(),
                bookingDTO.seatNumbers(),
                bookingDTO.showTime());
    }

    @Transactional
    public MessageDTO cancelTickets(String username , String movieTitle){
        Long userId = repo.findByUsername(username)
                .map(Users::getId)
                .orElseThrow(()-> new UserNotFound("User not found"));
        Long movieId = moviesRepo.findByTitle(movieTitle)
                .map(Movies::getId)
                .orElseThrow(()-> new UserNotFound("Movie not found"));
        bookingRepo.deleteByUserIdAndMovieId(userId,movieId);
        return new MessageDTO(HttpStatus.ACCEPTED,movieTitle+" has been canceled");
    }

    public AvailableSeatsDTO availableSeats(){
        List<String> seatsAcutal = seatsRepo.findAll().stream().map(Seats::getSeatNumbers).toList();
        List<Long> bookedIds = bookingRepo.findAll().stream().flatMap(b -> b.getSeatId().stream()).toList();
        List<String> bookedSeats = seatsRepo.findSeatsById(bookedIds).stream().toList();
        List<String> availableSeats = seatsAcutal.stream().filter(seat -> !bookedSeats.contains(seat)).toList();
        return new AvailableSeatsDTO(availableSeats.size() , availableSeats);
    }
}
