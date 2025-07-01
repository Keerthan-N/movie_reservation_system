package com.project.movie.repository;

import com.project.movie.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo  extends JpaRepository<Booking ,Long> {

    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
