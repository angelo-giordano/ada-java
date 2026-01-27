package com.example.domain.shared.exception;

/**
 * Exceção quando entidade não é encontrada
 */
public class NotFoundException extends DomainException {
    
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException forEntity(String entityName, String id) {
        return new NotFoundException(
            String.format("%s não encontrado(a) com ID: %s", entityName, id)
        );
    }
}