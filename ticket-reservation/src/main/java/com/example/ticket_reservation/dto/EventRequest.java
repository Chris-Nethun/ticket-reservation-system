package com.example.ticket_reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EventRequest(
        @NotBlank(message = "Event name is required") String name,
        @NotBlank(message = "Venue is required") String venue,
        @NotNull(message = "Date is required") LocalDate date,
        @Positive(message = "Total seats must be greater than zero") int totalSeats,
        @Positive(message = "Ticket price must be greater than zero") BigDecimal ticketPrice,
        @NotBlank(message = "Organizer is required") String organizer
) {
}
