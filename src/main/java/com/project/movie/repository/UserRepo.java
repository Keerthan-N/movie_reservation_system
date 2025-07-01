package com.project.movie.repository;

import com.project.movie.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepo  extends JpaRepository<Users,Long> {

    Users findByUsernameAndPassword(String name,String password);
    Optional<Users> findByUsername(String name);

}
