package com.example.domain.shared.event;

/**
 * Interface para subscribers de eventos
 */
@FunctionalInterface
public interface DomainEventSubscriber<T extends DomainEvent> {
    
    /**
     * Manipula o evento
     */
    void handle(DomainEvent event);
}