package com.example.domain.lending.event;

import com.example.domain.lending.model.LoanId;
import com.example.domain.catalog.model.BookId;
import com.example.domain.user.model.UserId;
import com.example.domain.shared.event.DomainEvent;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Evento: Empréstimo foi criado
 */
public class LoanCreatedEvent implements DomainEvent {
    
    private final LoanId loanId;
    private final BookId bookId;
    private final UserId userId;
    private final LocalDate dueDate;
    private final Instant occurredOn;

    public LoanCreatedEvent(LoanId loanId, BookId bookId, UserId userId, LocalDate dueDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.userId = userId;
        this.dueDate = dueDate;
        this.occurredOn = Instant.now();
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "LoanCreatedEvent{" +
                "loanId=" + loanId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", dueDate=" + dueDate +
                ", occurredOn=" + occurredOn +
                '}';
    }
}