package com.example.domain.lending.model;

/**
 * Enum para status do empréstimo
 */
public enum LoanStatus {
    ACTIVE("Ativo"),
    RETURNED("Devolvido"),
    OVERDUE("Atrasado");

    private final String displayName;

    LoanStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}