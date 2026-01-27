package com.example.application.dto;

import java.time.LocalDate;

/**
 * DTO para transferência de dados de Loan
 */
public record LoanDTO(
    String id,
    String userId,
    String userName,
    String bookId,
    String bookTitle,
    LocalDate borrowedAt,
    LocalDate dueDate,
    LocalDate returnedAt,
    String status,
    double fineAmount
) {
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    public boolean isOverdue() {
        return isActive() && LocalDate.now().isAfter(dueDate);
    }
}