package com.example.application.catalog.command;

// Command
public record BorrowBookCommand(
    String userId,
    String bookId
) {}