package com.example.domain.catalog.event;

import java.time.Instant;

import com.example.domain.catalog.model.BookId;
import com.example.domain.shared.event.DomainEvent;
import com.example.domain.user.model.UserId;

/**
 * Evento: Livro foi emprestado (usado pelo contexto de catálogo)
 */
public class BookBorrowedEvent implements DomainEvent {
    
    private final BookId bookId;
    private final UserId userId;
    private final Instant occurredOn;

    public BookBorrowedEvent(BookId bookId, UserId userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.occurredOn = Instant.now();
    }

    public BookId getBookId() {
        return bookId;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "BookBorrowedEvent{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", occurredOn=" + occurredOn +
                '}';
    }
}