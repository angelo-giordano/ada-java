package com.example.domain.catalog.model;

import java.util.Objects;

/**
 * Value Object para Autor
 */
public class Author {
    
    private final String name;

    public Author(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do autor não pode ser vazio");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Nome do autor muito longo (máx 255 caracteres)");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}