package com.example.domain.catalog.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object para ISBN (International Standard Book Number)
 * Aceita ISBN-10 ou ISBN-13
 */
public class ISBN {
    
    private static final Pattern ISBN_13_PATTERN = Pattern.compile("^\\d{13}$");
    private static final Pattern ISBN_10_PATTERN = Pattern.compile("^\\d{10}$");
    
    private final String value;

    public ISBN(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN não pode ser nulo ou vazio");
        }
        
        // Remove hífens e espaços
        String cleaned = value.replaceAll("[-\\s]", "");
        
        if (!isValid(cleaned)) {
            throw new IllegalArgumentException("ISBN inválido: " + value);
        }
        
        this.value = cleaned;
    }

    private boolean isValid(String isbn) {
        return ISBN_13_PATTERN.matcher(isbn).matches() || 
               ISBN_10_PATTERN.matcher(isbn).matches();
    }

    public String getValue() {
        return value;
    }

    public boolean isISBN13() {
        return value.length() == 13;
    }

    public boolean isISBN10() {
        return value.length() == 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        ISBN isbn = (ISBN) o;
        return Objects.equals(value, isbn.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}