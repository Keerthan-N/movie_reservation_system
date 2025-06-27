package com.project.movie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "theatres")
public class Theatres {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theatre_id",nullable = false)
    private Long theatreId;

    @Column(name = "theatre_name",nullable = false)
    private String theatreName;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "seats",nullable = false)
    private int seats;

    @Column(name = "pricing",nullable = false)
    private double pricing;

}
