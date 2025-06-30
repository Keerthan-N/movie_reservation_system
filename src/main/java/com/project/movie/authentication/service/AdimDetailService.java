package com.project.movie.authentication.service;

import com.project.movie.authentication.repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdimDetailService implements UserDetailsService {

    private final AdminRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(repo.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("Admin not found"));
    }
}
