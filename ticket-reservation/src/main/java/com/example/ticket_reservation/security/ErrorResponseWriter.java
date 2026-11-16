package com.example.ticket_reservation.security;

import com.example.ticket_reservation.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public final class ErrorResponseWriter {

    private ErrorResponseWriter() {
    }

    public static void write(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse payload = new ErrorResponse(
                status.value(),
                message,
                "/",
                LocalDateTime.now()
        );

        response.getWriter().write("{\"status\":" + payload.status() + ",\"message\":\"" + payload.message() + "\",\"path\":\"" + payload.path() + "\",\"timestamp\":\"" + payload.timestamp() + "\"}");
    }
}
