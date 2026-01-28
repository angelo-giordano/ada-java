package com.example.application.dto.mapper;

import java.time.LocalDate;

import com.example.application.dto.LoanDTO;
import com.example.domain.catalog.model.Book;
import com.example.domain.lending.model.Loan;
import com.example.domain.shared.valueobject.Money;
import com.example.domain.user.model.User;

/**
 * Mapper para converter Loan (domain) <-> LoanDTO (application)
 */
public class LoanMapper {

    public static LoanDTO toDTO(Loan loan, User user, Book book) {
        if (loan == null) {
            return null;
        }

        // Calcula multa atual
        Money fine = loan.calculateFine(LocalDate.now());

        return new LoanDTO(
            loan.getId().getValue(),
            user.getId().getValue(),
            user.getName(),
            book.getId().getValue(),
            book.getTitle(),
            loan.getBorrowedAt(),
            loan.getDueDate(),
            loan.getReturnedAt(),
            loan.getStatus().name(),
            fine.getAmount().doubleValue()
        );
    }

    public static LoanDTO toDTO(Loan loan, User user, Book book, Money fine) {
        if (loan == null) {
            return null;
        }

        return new LoanDTO(
            loan.getId().getValue(),
            user.getId().getValue(),
            user.getName(),
            book.getId().getValue(),
            book.getTitle(),
            loan.getBorrowedAt(),
            loan.getDueDate(),
            loan.getReturnedAt(),
            loan.getStatus().name(),
            fine.getAmount().doubleValue()
        );
    }
}