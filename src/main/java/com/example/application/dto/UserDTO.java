package com.example.application.dto;

/**
 * DTO para transferência de dados de User
 */
public record UserDTO(
    String id,
    String name,
    String email,
    String cpf,
    String userType,
    int maxBooks,
    int loanDays
) {}