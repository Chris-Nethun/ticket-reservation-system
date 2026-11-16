package com.example.ticket_reservation.config;

import com.example.ticket_reservation.model.AppUser;
import com.example.ticket_reservation.model.Event;
import com.example.ticket_reservation.model.Role;
import com.example.ticket_reservation.repository.EventRepository;
import com.example.ticket_reservation.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Admin");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            AppUser organizer = new AppUser();
            organizer.setUsername("organizer");
            organizer.setPassword(passwordEncoder.encode("org123"));
            organizer.setFullName("Event Organizer");
            organizer.setRole(Role.ORGANIZER);
            userRepository.save(organizer);
        }

        if (eventRepository.count() == 0) {
            Event event1 = new Event();
            event1.setName("Spring Boot Workshop");
            event1.setVenue("Colombo 07");
            event1.setDate(LocalDate.now().plusDays(10));
            event1.setTotalSeats(100);
            event1.setAvailableSeats(100);
            event1.setTicketPrice(new BigDecimal("1500.00"));
            event1.setOrganizer("organizer");
            eventRepository.save(event1);

            Event event2 = new Event();
            event2.setName("Tech Meetup");
            event2.setVenue("Kandy");
            event2.setDate(LocalDate.now().plusDays(20));
            event2.setTotalSeats(80);
            event2.setAvailableSeats(80);
            event2.setTicketPrice(new BigDecimal("1200.00"));
            event2.setOrganizer("organizer");
            eventRepository.save(event2);
        }
    }
}
