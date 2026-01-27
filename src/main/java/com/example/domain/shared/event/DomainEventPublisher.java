package com.example.domain.shared.event;

/**
 * Interface para publicar eventos de domínio
 * Hoje: in-memory
 * Amanhã: RabbitMQ/Kafka
 */
public interface DomainEventPublisher {
    
    /**
     * Publica um evento
     */
    void publish(DomainEvent event);
    
    /**
     * Registra um subscriber para um tipo de evento
     */
    <T extends DomainEvent> void subscribe(Class<T> eventType, DomainEventSubscriber<T> subscriber);
}