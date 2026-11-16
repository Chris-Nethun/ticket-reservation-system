package com.example.ticket_reservation.service;

import com.example.ticket_reservation.dto.AuthResponse;
import com.example.ticket_reservation.dto.LoginRequest;
import com.example.ticket_reservation.dto.RegisterRequest;
import com.example.ticket_reservation.dto.UserResponse;
import com.example.ticket_reservation.exception.ApiException;
import com.example.ticket_reservation.model.AppUser;
import com.example.ticket_reservation.model.Role;
import com.example.ticket_reservation.repository.UserRepository;
import com.example.ticket_reservation.security.JwtService;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);

        AppUser user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new ApiException("User not found"));

        return new AuthResponse(
                token,
                "Bearer",
                user.getUsername(),
                user.getRole().name(),
                jwtService.getExpirationMs() / 1000
        );
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ApiException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setRole(request.role() != null ? request.role() : Role.CUSTOMER);

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getRole());
    }

    public UserResponse me(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getRole());
    }
}
