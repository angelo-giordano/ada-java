package com.example.api.response;

import java.time.LocalDateTime;

/**
 * Response wrapper genérico para padronizar respostas da API
 */
public record ApiResponse<T>(
    boolean success,
    T data,
    String message,
    LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message, LocalDateTime.now());
    }
}