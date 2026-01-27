package com.example.domain.lending.policy;

import com.example.domain.user.model.User;

/**
 * Política de empréstimo para professores
 */
public class ProfessorLoanPolicy implements LoanPolicy {
    
    private static final int MAX_BOOKS = 5;
    private static final int LOAN_DAYS = 30;

    @Override
    public int getMaxBooks() {
        return MAX_BOOKS;
    }

    @Override
    public int getLoanDays() {
        return LOAN_DAYS;
    }

    @Override
    public boolean canBorrow(User user, int currentActiveLoans) {
        return currentActiveLoans < MAX_BOOKS;
    }
}
