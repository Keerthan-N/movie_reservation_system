package com.project.movie.model;

import com.project.movie.model.composite_keys.UserMovieTheatreId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_movie_theatre")
@IdClass(UserMovieTheatreId.class)
public class UserMovieTheatre {

    @Id
    private Long userId;
    @Id
    private Long movieId;
    @Id
    private Long theatreId;

    @Column(nullable = false)
    private LocalDateTime timings;

    @Column(nullable = false)
    private String seats;

}
