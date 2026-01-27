package com.example.domain.user.model;

/**
 * Enum para tipos de usuário
 * Cada tipo tem regras diferentes de empréstimo
 */
public enum UserType {
    STUDENT("Estudante", 3, 14),
    PROFESSOR("Professor", 5, 30),
    COMMON("Comum", 2, 7);

    private final String displayName;
    private final int maxBooks;
    private final int loanDays;

    UserType(String displayName, int maxBooks, int loanDays) {
        this.displayName = displayName;
        this.maxBooks = maxBooks;
        this.loanDays = loanDays;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public int getLoanDays() {
        return loanDays;
    }
}