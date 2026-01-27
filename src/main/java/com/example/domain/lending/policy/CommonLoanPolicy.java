package com.example.domain.lending.policy;

import com.example.domain.user.model.User;

/**
 * Política de empréstimo para usuários comuns
 */
public class CommonLoanPolicy implements LoanPolicy {
    
    private static final int MAX_BOOKS = 2;
    private static final int LOAN_DAYS = 7;

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