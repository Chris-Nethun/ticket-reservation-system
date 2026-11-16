package com.example.ticket_reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EventResponse(
        String id,
        String name,
        String venue,
        LocalDate date,
        int totalSeats,
        int availableSeats,
        BigDecimal ticketPrice,
        String organizer
) {
}
