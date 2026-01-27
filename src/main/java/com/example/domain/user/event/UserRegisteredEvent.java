package com.example.domain.user.event;

import com.example.domain.user.model.UserId;
import com.example.domain.user.model.UserType;
import com.example.domain.shared.event.DomainEvent;
import java.time.Instant;

/**
 * Evento: Usuário foi registrado
 */
public class UserRegisteredEvent implements DomainEvent {
    
    private final UserId userId;
    private final String name;
    private final String email;
    private final UserType userType;
    private final Instant occurredOn;

    public UserRegisteredEvent(UserId userId, String name, String email, UserType userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.occurredOn = Instant.now();
    }

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userType=" + userType +
                ", occurredOn=" + occurredOn +
                '}';
    }
}