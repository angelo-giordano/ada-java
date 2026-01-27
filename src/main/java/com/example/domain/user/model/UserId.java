package com.example.domain.user.model;

import com.example.domain.shared.valueobject.EntityId;

/**
 * Value Object para ID de User
 */
public class UserId extends EntityId {
    
    public UserId(String value) {
        super(value);
    }

    public UserId() {
        super();
    }

    public static UserId generate() {
        return new UserId();
    }

    public static UserId of(String value) {
        return new UserId(value);
    }
}