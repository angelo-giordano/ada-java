package com.example.api.request;

/**
 * Request DTO para cadastrar livro
 */
public record RegisterBookRequest(
    String isbn,
    String title,
    String authorName,
    String category,
    int totalQuantity
) {}