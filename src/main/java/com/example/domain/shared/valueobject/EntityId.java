package com.example.domain.shared.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object base para IDs de entidades
 * Imutável e com validação
 */
public abstract class EntityId {
    
    private final String value;

    protected EntityId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
        this.value = value;
    }

    protected EntityId() {
        this.value = UUID.randomUUID().toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return Objects.equals(value, entityId.value);
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