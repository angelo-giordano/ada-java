package com.example.application.catalog.command;

public record RegisterUserCommand(
    String name,
    String email,
    String cpf,
    String userType
) {}