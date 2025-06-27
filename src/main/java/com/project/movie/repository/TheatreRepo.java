package com.project.movie.repository;

import com.project.movie.model.Theatres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepo extends JpaRepository<Theatres,Long> {
}
