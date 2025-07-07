package com.project.movie.repository;

import com.project.movie.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepo  extends JpaRepository<Booking ,Long> {

    void deleteByUserIdAndMovieId(Long userId, Long movieId);
    Booking findByUserIdAndMovieId(Long userId, Long movieId);

    @Query("""
            SELECT SUM(b.price) FROM Booking b WHERE b.date = :date
            """)
    double findRevenue(LocalDate date);
}
