package com.project.movie.model;

import com.project.movie.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;
    private String displayName;
    private Roles roles;
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();
    @Builder.Default
    private LocalDate updatedAt = LocalDate.now();
}
