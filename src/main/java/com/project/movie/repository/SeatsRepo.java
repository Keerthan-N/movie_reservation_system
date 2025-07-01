package com.project.movie.repository;

import com.project.movie.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface SeatsRepo extends JpaRepository<Seats,Long> {

    @Query("""
            SELECT s.id FROM Seats s WHERE s.seatNumbers IN :seats
            """)
    List<Long> findBySeatNumbers(List<String> seats);
}
