package com.project.movie.repository;

import com.project.movie.model.UserMovieTheatre;
import com.project.movie.model.composite_keys.UserMovieTheatreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieTheatreRepo extends JpaRepository<UserMovieTheatre, UserMovieTheatreId> {
}
