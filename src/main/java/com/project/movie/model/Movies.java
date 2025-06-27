package com.project.movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id",nullable = false)
    private Long movieId;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "release_date",nullable = false)
    private LocalDate releaseDate;

    @Column(name = "duration",nullable = false)
    private  int duration;

    @Column(name = "movie_poster",nullable = false)
    private String moviePoster;

    @Column(name = "genre")
    private String genre;

    @Column(name = "language")
    private String language;

}
