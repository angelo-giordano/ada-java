package com.example.application.dto;

/**
 * DTO para transferência de dados de Book
 */
public record BookDTO(
    String id,
    String isbn,
    String title,
    String authorName,
    String category,
    int availableQuantity,
    int totalQuantity
) {
    public boolean isAvailable() {
        return availableQuantity > 0;
    }
}