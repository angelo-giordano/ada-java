package com.example.domain.catalog.model;

import java.util.Objects;

/**
 * Value Object para Categoria
 */
public class Category {
    
    private final String name;

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Nome da categoria muito longo (máx 100 caracteres)");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
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