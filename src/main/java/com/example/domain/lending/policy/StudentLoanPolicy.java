package com.example.domain.lending.policy;

import com.example.domain.user.model.User;

/**
 * Política de empréstimo para estudantes
 */
public class StudentLoanPolicy implements LoanPolicy {
    
    private static final int MAX_BOOKS = 3;
    private static final int LOAN_DAYS = 14;

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
