package com.example.infrastructure.messaging;

import com.example.domain.shared.event.DomainEvent;
import com.example.domain.shared.event.DomainEventPublisher;
import com.example.domain.shared.event.DomainEventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementação in-memory do Event Bus
 * Hoje: eventos síncronos em memória
 * Amanhã: trocar por RabbitMQ/Kafka sem mudar o domain
 */
@Component
public class InMemoryEventBus implements DomainEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(InMemoryEventBus.class);
    
    private final Map<Class<? extends DomainEvent>, List<DomainEventSubscriber<?>>> subscribers;

    public InMemoryEventBus() {
        this.subscribers = new ConcurrentHashMap<>();
    }

    @Override
    public void publish(DomainEvent event) {
        logger.info("Publishing event: {}", event.eventName());
        
        Class<? extends DomainEvent> eventType = event.getClass();
        List<DomainEventSubscriber<?>> eventSubscribers = subscribers.get(eventType);

        if (eventSubscribers != null && !eventSubscribers.isEmpty()) {
            for (DomainEventSubscriber subscriber : eventSubscribers) {
                try {
                    subscriber.handle(event);
                    logger.debug("Event {} handled by subscriber", event.eventName());
                } catch (Exception e) {
                    logger.error("Error handling event {}: {}", event.eventName(), e.getMessage(), e);
                }
            }
        } else {
            logger.debug("No subscribers for event: {}", event.eventName());
        }
    }

    @Override
    public <T extends DomainEvent> void subscribe(Class<T> eventType, DomainEventSubscriber<T> subscriber) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
        logger.info("Subscriber registered for event type: {}", eventType.getSimpleName());
    }
}