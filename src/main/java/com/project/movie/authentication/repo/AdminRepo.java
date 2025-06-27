package com.project.movie.authentication.repo;

import com.project.movie.authentication.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Admin findByUsername(String name);

}
