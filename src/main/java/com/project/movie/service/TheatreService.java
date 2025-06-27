package com.project.movie.service;

import com.project.movie.dto.TheatreDTO;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.model.Theatres;
import com.project.movie.repository.TheatreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepo theatreRepo;

    public CreatedSuccessfullyDTO addTheatre(TheatreDTO theatreDTO){
        Theatres theatres = Theatres.builder()
                .theatreName(theatreDTO.theatreName())
                .location(theatreDTO.locaion())
                .seats(theatreDTO.seats())
                .pricing(theatreDTO.pricing())
                .build();
        if(!ObjectUtils.isEmpty(theatres)) theatreRepo.save(theatres);
        return new CreatedSuccessfullyDTO(HttpStatus.OK.value(), theatres);
    }

    public List<Theatres> getAllTheatres(){
        return theatreRepo.findAll();
    }


}
