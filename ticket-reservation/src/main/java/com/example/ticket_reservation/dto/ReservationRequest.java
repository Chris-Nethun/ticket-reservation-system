package com.example.ticket_reservation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        @NotBlank(message = "Event ID is required") String eventId,
        @Min(value = 1, message = "Seats must be at least 1")
        @Max(value = 10, message = "Seats must be at most 10") int seats
) {
}
