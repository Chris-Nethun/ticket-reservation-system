package com.example.ticket_reservation.service;

import com.example.ticket_reservation.dto.StatsResponse;
import com.example.ticket_reservation.dto.UserResponse;
import com.example.ticket_reservation.exception.ApiException;
import com.example.ticket_reservation.model.AppUser;
import com.example.ticket_reservation.repository.EventRepository;
import com.example.ticket_reservation.repository.ReservationRepository;
import com.example.ticket_reservation.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;

    public AdminService(
            UserRepository userRepository,
            EventRepository eventRepository,
            ReservationRepository reservationRepository
    ) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getRole()))
                .toList();
    }

    public StatsResponse getStats() {
        long totalUsers = userRepository.count();
        long totalEvents = eventRepository.count();
        long totalReservations = reservationRepository.count();
        return new StatsResponse(totalUsers, totalEvents, totalReservations);
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
