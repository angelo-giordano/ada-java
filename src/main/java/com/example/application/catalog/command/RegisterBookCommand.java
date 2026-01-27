package com.example.application.catalog.command;

// Command
public record RegisterBookCommand(
    String isbn,
    String title,
    String authorName,
    String category,
    int totalQuantity
) {}