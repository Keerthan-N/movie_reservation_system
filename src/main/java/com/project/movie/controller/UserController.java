package com.project.movie.controller;

import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.UserDTO;
import com.project.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @PostMapping("/register-user")
    public ResponseEntity<MessageDTO> register(@RequestBody UserDTO dto){
        return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<MessageDTO> login(@PathVariable String username , @PathVariable String password){
        return ResponseEntity.ok(service.login(username,password));
    }
}
