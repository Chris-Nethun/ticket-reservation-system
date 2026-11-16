package com.example.ticket_reservation.service;

import com.example.ticket_reservation.dto.EventRequest;
import com.example.ticket_reservation.dto.EventResponse;
import com.example.ticket_reservation.exception.ApiException;
import com.example.ticket_reservation.model.Event;
import com.example.ticket_reservation.repository.EventRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse createEvent(EventRequest request) {
        Event event = new Event();
        event.setName(request.name());
        event.setVenue(request.venue());
        event.setDate(request.date());
        event.setTotalSeats(request.totalSeats());
        event.setAvailableSeats(request.totalSeats());
        event.setTicketPrice(request.ticketPrice());
        event.setOrganizer(request.organizer());

        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public EventResponse getEventById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException("Event not found"));
        return mapToResponse(event);
    }

    public EventResponse updateEvent(String id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException("Event not found"));

        event.setName(request.name());
        event.setVenue(request.venue());
        event.setDate(request.date());
        event.setTotalSeats(request.totalSeats());
        event.setTicketPrice(request.ticketPrice());
        event.setOrganizer(request.organizer());

        if (event.getAvailableSeats() > request.totalSeats()) {
            event.setAvailableSeats(request.totalSeats());
        }

        Event updated = eventRepository.save(event);
        return mapToResponse(updated);
    }

    public void deleteEvent(String id) {
        if (!eventRepository.existsById(id)) {
            throw new ApiException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    private EventResponse mapToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getVenue(),
                event.getDate(),
                event.getTotalSeats(),
                event.getAvailableSeats(),
                event.getTicketPrice(),
                event.getOrganizer()
        );
    }
}
