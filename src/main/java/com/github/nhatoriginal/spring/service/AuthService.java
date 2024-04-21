package com.github.nhatoriginal.spring.service;

import com.github.nhatoriginal.spring.dto.AuthLoginDto;
import com.github.nhatoriginal.spring.dto.AuthRegisterDto;
import com.github.nhatoriginal.spring.dto.AuthResponseDto;
import com.github.nhatoriginal.spring.model.Role;
import com.github.nhatoriginal.spring.model.User;
import com.github.nhatoriginal.spring.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.github.nhatoriginal.spring.config.SecurityConfig.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

  public String register(AuthRegisterDto authRegisterDto) {
    if (userRepository.findByEmail(authRegisterDto.getEmail()) != null) {
        throw new IllegalArgumentException("Email already in use");
    }

    User user = User.builder()
            .email(authRegisterDto.getEmail())
            .hashedPassword(passwordEncoder.encode(authRegisterDto.getPassword()))
            .fullName(authRegisterDto.getFullName())
            .dateOfBirth(authRegisterDto.getDateOfBirth())
            .gender(authRegisterDto.getGender())
            .phone(authRegisterDto.getPhone())
            .role(Role.CUSTOMER)
            .build();
    userRepository.save(user);
    return "User registered successfully";
}

    public AuthResponseDto login (AuthLoginDto authLoginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginDto.getEmail(), authLoginDto.getPassword()));
        var jwtToken = jwtService.generateToken(userService.loadUserByUsername(authLoginDto.getEmail()));
        return AuthResponseDto.builder().accessToken(jwtToken).build();
    }




}