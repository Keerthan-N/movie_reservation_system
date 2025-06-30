package com.project.movie.service;

import com.project.movie.dto.MessageDTO;
import com.project.movie.dto.UserDTO;
import com.project.movie.exceptions.UserNotFound;
import com.project.movie.model.Users;
import com.project.movie.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;

    public MessageDTO register(UserDTO dto){
        if(!ObjectUtils.isEmpty(dto)){
            Users user = Users.builder()
                    .username(dto.username())
                    .password(dto.password())
                    .displayName(dto.displayName())
                    .roles(dto.role())
                    .build();
            repo.save(user);
        }
        return new MessageDTO(HttpStatus.CREATED, dto.displayName()+" has been added");
    }

    public MessageDTO login(String username , String password){
        if(StringUtils.hasLength(username) && StringUtils.hasLength(password)){
            Optional.ofNullable(repo.findByUsernameAndPassword(username,password))
                    .orElseThrow(()-> new UserNotFound("User Not found"));
            return new MessageDTO(HttpStatus.OK,"Login Successfull");
        }
        return new MessageDTO(HttpStatus.BAD_REQUEST,"Username / Password cannot be empty");
    }

}
