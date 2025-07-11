package com.project.movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    private String bookingId;
    private Long userId;
    private Long movieId;
    private String showTime;
    private LocalDate date;
    private List<Long> seatId;
    private double price;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
