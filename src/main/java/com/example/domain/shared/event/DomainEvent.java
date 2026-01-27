package com.example.domain.shared.event;

import java.time.Instant;

/**
 * Interface base para todos os eventos de domínio
 */
public interface DomainEvent {
    
    /**
     * Momento em que o evento ocorreu
     */
    Instant occurredOn();
    
    /**
     * Nome do evento (útil para logging e debugging)
     */
    default String eventName() {
        return this.getClass().getSimpleName();
    }
}