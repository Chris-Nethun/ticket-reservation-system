package com.example.ticket_reservation.repository;

import com.example.ticket_reservation.model.Reservation;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByUserId(String userId);
    List<Reservation> findByEventId(String eventId);
    boolean existsByEventIdAndUserId(String eventId, String userId);
}
