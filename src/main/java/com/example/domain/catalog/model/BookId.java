package com.example.domain.catalog.model;

import com.example.domain.shared.valueobject.EntityId;

/**
 * Value Object para ID de Book
 */
public class BookId extends EntityId {
    
    public BookId(String value) {
        super(value);
    }

    public BookId() {
        super();
    }

    public static BookId generate() {
        return new BookId();
    }

    public static BookId of(String value) {
        return new BookId(value);
    }
}