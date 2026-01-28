package com.example.domain.lending.event;

import java.time.Instant;
import java.time.LocalDate;

import com.example.domain.catalog.model.BookId;
import com.example.domain.lending.model.LoanId;
import com.example.domain.shared.event.DomainEvent;
import com.example.domain.shared.valueobject.Money;

/**
 * Evento: Livro foi devolvido
 */
public class BookReturnedEvent implements DomainEvent {
    
    private final LoanId loanId;
    private final BookId bookId;
    private final LocalDate returnedAt;
    private final Money fine;
    private final Instant occurredOn;

    public BookReturnedEvent(LoanId loanId, BookId bookId, LocalDate returnedAt, Money fine) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.returnedAt = returnedAt;
        this.fine = fine;
        this.occurredOn = Instant.now();
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public Money getFine() {
        return fine;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "BookReturnedEvent{" +
                "loanId=" + loanId +
                ", bookId=" + bookId +
                ", returnedAt=" + returnedAt +
                ", fine=" + fine +
                ", occurredOn=" + occurredOn +
                '}';
    }
}