package com.project.movie.controller;

import com.project.movie.dto.TheatreDTO;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.model.Theatres;
import com.project.movie.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/theatre")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService service;

    @PostMapping("/add-theatre")
    public CreatedSuccessfullyDTO addTheatres(@RequestBody TheatreDTO dto){
        return service.addTheatre(dto);
    }

    @GetMapping("/get-theatres")
    public List<Theatres> getAllTheatres(){
        return service.getAllTheatres();
    }

}
