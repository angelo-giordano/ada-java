package com.example.domain.shared.exception;

/**
 * Exceção para violações de regras de negócio
 */
public class BusinessRuleException extends DomainException {
    
    public BusinessRuleException(String message) {
        super(message);
    }
}