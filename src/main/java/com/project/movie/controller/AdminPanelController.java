package com.project.movie.controller;


import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.projection.AdminWatchDTO;
import com.project.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService service;

    @GetMapping("/get-booking-details")
    public ResponseEntity<List<AdminWatchDTO>> getBookingDetails(){
        return ResponseEntity.ok(service.getBookingDetails());
    }

    @GetMapping("/get-revenue/{date}")
    public ResponseEntity<MessageDTO> getRevenue(@PathVariable LocalDate date){
        return ResponseEntity.ok(service.getRevenue(date));
    }
}
