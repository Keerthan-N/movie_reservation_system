package com.project.movie.repository;

import com.project.movie.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatsRepo extends JpaRepository<Seats,Long> {

    @Query("""
            SELECT s.id FROM Seats s WHERE s.seatNumbers IN :seats
            """)
    List<Long> findBySeatNumbers(List<String> seats);


    @Query("""
            SELECT s.seatNumbers FROM Seats s WHERE s.id IN :ids
            """)
    List<String> findSeatsById(List<Long> ids);
}
