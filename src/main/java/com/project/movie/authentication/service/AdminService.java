package com.project.movie.authentication.service;

import com.project.movie.authentication.dto.AdminDTO;
import com.project.movie.authentication.model.Admin;
import com.project.movie.authentication.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepo repo;
    private final PasswordEncoder passwordEncoder;

    public String register(AdminDTO adminDTO){
        if(Optional.ofNullable(repo.findByUsername(adminDTO.username())).isPresent())
            throw new RuntimeException("Admin already exists");

        Admin admin = Admin.builder()
                .username(adminDTO.username())
                .password(passwordEncoder.encode(adminDTO.password()))
                .role("ADMIN")
                .build();

        repo.save(admin);
        return "Admin registered successfully";
    }

    public String registerMultipleAdmins(List<AdminDTO> adminDTOList){
        List<Admin> adminList = adminDTOList.stream().map(
                data -> {
                    if(Optional.ofNullable(repo.findByUsername(data.username())).isPresent())
                        throw new RuntimeException("Admin already exists");

                    Admin admin = Admin.builder()
                            .username(data.username())
                            .password(passwordEncoder.encode(data.password()))
                            .role("ADMIN")
                            .build();
                    return admin;
                }
        ).toList();
        return  adminDTOList.size()+" Admins registered successfully";
    }

}
