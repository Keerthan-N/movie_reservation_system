package com.project.movie.authentication.controller;

import com.project.movie.authentication.dto.AdminDTO;
import com.project.movie.authentication.dto.MessageDTO;
import com.project.movie.authentication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> register(@RequestBody AdminDTO adminDTO){
        return ResponseEntity.ok(service.register(adminDTO));
    }

    @PostMapping("/register-multiple")
    public ResponseEntity<MessageDTO> registerMultipleAdmins(@RequestBody List<AdminDTO> adminDTOList){
        return ResponseEntity.ok(service.registerMultipleAdmins(adminDTOList));
    }

}
