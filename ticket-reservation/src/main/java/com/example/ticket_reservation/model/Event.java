package com.example.ticket_reservation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String venue;

    @NotNull
    private LocalDate date;

    @Positive
    private int totalSeats;

    @PositiveOrZero
    private int availableSeats;

    @NotNull
    @Positive
    private BigDecimal ticketPrice;

    @NotBlank
    private String organizer;
}
