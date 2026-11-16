package com.example.ticket_reservation.dto;

public record AuthResponse(
        String token,
        String type,
        String username,
        String role,
        long expiresIn
) {
}
