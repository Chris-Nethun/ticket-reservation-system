package com.example.ticket_reservation.service;

import com.example.ticket_reservation.dto.ReservationRequest;
import com.example.ticket_reservation.dto.ReservationResponse;
import com.example.ticket_reservation.exception.ApiException;
import com.example.ticket_reservation.model.Event;
import com.example.ticket_reservation.model.Reservation;
import com.example.ticket_reservation.model.ReservationStatus;
import com.example.ticket_reservation.repository.EventRepository;
import com.example.ticket_reservation.repository.ReservationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    public ReservationService(ReservationRepository reservationRepository, EventRepository eventRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
    }

    public ReservationResponse createReservation(String userId, ReservationRequest request) {
        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new ApiException("Event not found"));

        if (event.getAvailableSeats() < request.seats()) {
            throw new ApiException("Not enough seats available");
        }

        if (reservationRepository.existsByEventIdAndUserId(request.eventId(), userId)) {
            throw new ApiException("Reservation already exists for this user and event");
        }

        BigDecimal amount = event.getTicketPrice().multiply(BigDecimal.valueOf(request.seats()));

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setEventId(request.eventId());
        reservation.setCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        reservation.setSeats(request.seats());
        reservation.setAmount(amount);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        event.setAvailableSeats(event.getAvailableSeats() - request.seats());
        eventRepository.save(event);

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    public List<ReservationResponse> getReservationsForUser(String userId) {
        return reservationRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    public List<ReservationResponse> getReservationsForEvent(String eventId) {
        return reservationRepository.findByEventId(eventId).stream().map(this::mapToResponse).toList();
    }

    public ReservationResponse cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiException("Reservation not found"));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ApiException("Reservation is already cancelled");
        }

        Event event = eventRepository.findById(reservation.getEventId())
                .orElseThrow(() -> new ApiException("Event for this reservation not found"));

        event.setAvailableSeats(event.getAvailableSeats() + reservation.getSeats());
        eventRepository.save(event);

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getEventId(),
                reservation.getCode(),
                reservation.getSeats(),
                reservation.getAmount(),
                reservation.getStatus()
        );
    }
}
