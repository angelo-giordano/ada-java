package com.example.domain.catalog.event;

import java.time.Instant;

import com.example.domain.catalog.model.BookId;
import com.example.domain.shared.event.DomainEvent;

/**
 * Evento: Livro foi cadastrado
 */
public class BookRegisteredEvent implements DomainEvent {
    
    private final BookId bookId;
    private final String title;
    private final String isbn;
    private final Instant occurredOn;

    public BookRegisteredEvent(BookId bookId, String title, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.occurredOn = Instant.now();
    }

    public BookId getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "BookRegisteredEvent{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}