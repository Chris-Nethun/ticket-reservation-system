package com.example.ticket_reservation.controller;

import com.example.ticket_reservation.dto.ReservationRequest;
import com.example.ticket_reservation.dto.ReservationResponse;
import com.example.ticket_reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            Principal principal,
            @Valid @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(reservationService.createReservation(principal.getName(), request));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(Principal principal) {
        return ResponseEntity.ok(reservationService.getReservationsForUser(principal.getName()));
    }

    @GetMapping("/events/{eventId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsForEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(reservationService.getReservationsForEvent(eventId));
    }
}
