package com.project.movie.model;

import com.project.movie.model.composite_keys.MovieTheatreId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "movie_theatre")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MovieTheatreId.class)
public class MovieTheatre {

    @Id
    private Long theatreId;

    @Id
    private Long movieId;

    @Column(nullable = false)
    private int seats;

}
