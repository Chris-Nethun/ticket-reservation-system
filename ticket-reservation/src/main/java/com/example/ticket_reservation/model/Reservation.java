package com.example.ticket_reservation.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String eventId;

    @NotBlank
    private String code;

    @Min(1)
    @Max(10)
    private int seats;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private ReservationStatus status;
}
