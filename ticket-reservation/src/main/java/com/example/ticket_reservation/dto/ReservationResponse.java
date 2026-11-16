package com.example.ticket_reservation.dto;

import com.example.ticket_reservation.model.ReservationStatus;
import java.math.BigDecimal;

public record ReservationResponse(
        String id,
        String userId,
        String eventId,
        String code,
        int seats,
        BigDecimal amount,
        ReservationStatus status
) {
}
