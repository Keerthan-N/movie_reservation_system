package com.project.movie.repository;

import com.project.movie.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MoviesRepo extends JpaRepository<Movies,Long> {
    Optional<Movies> findByTitle(String movies);
    void deleteByTitle(String title);
}
