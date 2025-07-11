package com.project.movie.service;

import com.google.zxing.WriterException;
import com.project.movie.dto.*;
import com.project.movie.dto.projection.AdminWatchDTO;
import com.project.movie.dto.projection.AvailableSeatsDTO;
import com.project.movie.dto.projection.BookingConfrimedDTO;
import com.project.movie.exceptions.custom_exceptions.CancelError;
import com.project.movie.exceptions.custom_exceptions.UserNotFound;
import com.project.movie.model.Booking;
import com.project.movie.model.Movies;
import com.project.movie.model.Seats;
import com.project.movie.model.Users;
import com.project.movie.repository.BookingRepo;
import com.project.movie.repository.MoviesRepo;
import com.project.movie.repository.SeatsRepo;
import com.project.movie.repository.UserRepo;
import com.project.movie.service.service_impl.AlphaCodeGenerator;
import com.project.movie.service.service_impl.PdfGeneratorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final MoviesRepo moviesRepo;
    private final SeatsRepo seatsRepo;
    private final BookingRepo bookingRepo;
    private final PdfGeneratorService pdfGeneratorService;

    private Long checkRemainHours(String time){
        String[] splitTime = time.trim().replaceAll("(?i)[a-z ]", "").split(":");
        LocalTime targetTime = LocalTime.of(Integer.parseInt(splitTime[0]),Integer.parseInt(splitTime[1]));
        LocalTime currentTime = LocalTime.now();
        return ChronoUnit.HOURS.between(currentTime,targetTime);
    }

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
        AlphaCodeGenerator alphaCodeGenerator = new AlphaCodeGenerator();
        LocalDate date = ObjectUtils.isEmpty(bookingDTO.date()) ? LocalDate.now() : bookingDTO.date();
        Long userId = repo.findByUsername(username)
                .map(Users::getId)
                .orElseThrow(()-> new UserNotFound("User not found"));
        Long movieId = moviesRepo.findByTitle(bookingDTO.movieTitle())
                .map(Movies::getId)
                .orElseThrow(()-> new UserNotFound("Movie not found"));
        List<Long> seatIds = seatsRepo.findBySeatNumbers(bookingDTO.seatNumbers());
        String bookingId = alphaCodeGenerator.generateCode();
        Booking booking = Booking.builder()
                .bookingId(bookingId)
                .userId(userId)
                .movieId(movieId)
                .date(date)
                .showTime(bookingDTO.showTime())
                .seatId(seatIds)
                .price(bookingDTO.price()).build();
        bookingRepo.save(booking);
        return new BookingConfrimedDTO("Bookig Confrimed", bookingDTO.movieTitle(),seatIds.size(), bookingDTO.seatNumbers(), bookingDTO.showTime(), date);
    }

    @Transactional
    public MessageDTO cancelTickets(String username , String movieTitle){
        Long userId = repo.findByUsername(username)
                .map(Users::getId)
                .orElseThrow(()-> new UserNotFound("User not found"));
        Long movieId = moviesRepo.findByTitle(movieTitle)
                .map(Movies::getId)
                .orElseThrow(()-> new UserNotFound("Movie not found"));
       Booking booking = bookingRepo.findByUserIdAndMovieId(userId,movieId);
        if(booking.getDate().equals(LocalDate.now())){
            if (checkRemainHours(booking.getShowTime()) <= 4)
                throw new CancelError("Cannot cancel the ticket as the movie is booked for today");
        }
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

    public List<AdminWatchDTO> getBookingDetails(){
        return bookingRepo.findAll().stream().map( data -> {
            String bookingId = data.getBookingId();
            String movieTitle = moviesRepo.findById(data.getMovieId()).get().getTitle();
            List<String> bookedSeatsId = seatsRepo.findSeatsById(data.getSeatId());
            String showtime = data.getShowTime();
            String userName = repo.findById(data.getUserId()).get().getUsername();
            LocalDate date = data.getDate();
            double price = data.getPrice();
            return new AdminWatchDTO(bookingId,movieTitle,bookedSeatsId,showtime,userName,date,price);
        }).toList();
    }

    public MessageDTO getRevenue(LocalDate date){
        return new MessageDTO(HttpStatus.OK,"Revenue for "+date+" booking: ₹"+bookingRepo.findRevenue(date));
    }

    public void downloadTicket(OutputStream outputStream ,String username, String title) throws IOException, WriterException {
        Long userId = repo.findByUsername(username)
                .map(Users::getId)
                .orElseThrow(()-> new UserNotFound("User not found"));
        Long movieId = moviesRepo.findByTitle(title)
                .map(Movies::getId)
                .orElseThrow(()-> new UserNotFound("Movie not found"));
        pdfGeneratorService.exportPdf(outputStream,userId,movieId);
    }
}
