package com.example.api.response;

import java.time.LocalDateTime;

/**
 * Response para erros
 */
public record ErrorResponse(
    String message,
    String details,
    LocalDateTime timestamp
) {
    public ErrorResponse(String message) {
        this(message, null, LocalDateTime.now());
    }

    public ErrorResponse(String message, String details) {
        this(message, details, LocalDateTime.now());
    }
}