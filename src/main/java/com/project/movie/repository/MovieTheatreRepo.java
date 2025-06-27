package com.project.movie.repository;

import com.project.movie.model.MovieTheatre;
import com.project.movie.model.composite_keys.MovieTheatreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTheatreRepo extends JpaRepository<MovieTheatre, MovieTheatreId> {
}
