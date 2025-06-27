package com.project.movie.controller;

import com.project.movie.dto.UserDTO;
import com.project.movie.dto.projection.UserProjection;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UsersService service;

    @PostMapping("/register")
    public CreatedSuccessfullyDTO register(@RequestBody UserDTO user){
        return service.register(user);
    }

    @GetMapping("/get-users")
    public List<UserProjection> getUsers(){
        return service.getUsers();
    }

    @GetMapping("/get-user/{id}")
    public UserProjection getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

}
