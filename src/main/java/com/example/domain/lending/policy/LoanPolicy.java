package com.example.domain.lending.policy;

import com.example.domain.user.model.User;

/**
 * Interface para políticas de empréstimo
 * Strategy Pattern - Open/Closed Principle
 */
public interface LoanPolicy {
    
    /**
     * Número máximo de livros que podem ser emprestados simultaneamente
     */
    int getMaxBooks();
    
    /**
     * Número de dias do período de empréstimo
     */
    int getLoanDays();
    
    /**
     * Verifica se o usuário pode fazer um novo empréstimo
     */
    boolean canBorrow(User user, int currentActiveLoans);
}