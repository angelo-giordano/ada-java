package com.example.api.request;

/**
 * Request DTO para emprestar livro
 */
public record BorrowBookRequest(
    String userId,
    String bookId
) {}