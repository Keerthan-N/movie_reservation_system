package com.project.movie.service;

import com.project.movie.dto.UserDTO;
import com.project.movie.dto.projection.UserProjection;
import com.project.movie.dto.responses.CreatedSuccessfullyDTO;
import com.project.movie.exceptions.UserNotFoundException;
import com.project.movie.model.User;
import com.project.movie.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepo usersRepo;

    public CreatedSuccessfullyDTO register(UserDTO user){
        User users = User.builder()
                .username(user.name())
                .password(user.password())
                .email(user.email())
                .phoneNo(user.phoneNo())
                .displayName(user.displayName())
                .build();
        if(!ObjectUtils.isEmpty(users)){
             usersRepo.save(users);
        }
        return new CreatedSuccessfullyDTO(HttpStatus.OK.value(),users);
    }

    public List<UserProjection> getUsers(){
        List<UserProjection> userDTOS = usersRepo.findAll().stream().map(
                data -> new UserProjection(data.getUsername(), data.getDisplayName(),data.getEmail(), data.getPhoneNo())
        ).toList();
        if(userDTOS.isEmpty()) throw  new UserNotFoundException("No users found");
      return userDTOS;
    }

    public UserProjection getUserById(Long id){
        return Optional.ofNullable(usersRepo.findById(id))
                .get().map(data ->
                        new UserProjection(data.getUsername(), data.getDisplayName(),data.getEmail(), data.getPhoneNo()))
                .orElseThrow(()-> new UserNotFoundException("User Not found"));
    }

}
