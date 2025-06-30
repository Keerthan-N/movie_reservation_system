package com.project.movie.dto;

import com.project.movie.enums.Roles;

public record UserDTO(String username, String password, String displayName , Roles role) {
}
