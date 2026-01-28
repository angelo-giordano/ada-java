package com.example.api.request;

/**
 * Request DTO para cadastrar usuário
 */
public record RegisterUserRequest(
    String name,
    String email,
    String cpf,
    String userType
) {}