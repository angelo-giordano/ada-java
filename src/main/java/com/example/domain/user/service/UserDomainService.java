package com.example.domain.user.service;

import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserType;

/**
 * Domain Service para User
 */
public class UserDomainService {

    /**
     * Valida se usuário pode ser promovido a outro tipo
     */
    public boolean canUpgradeUserType(User user, UserType newType, int totalBooksRead) {
        // Estudante pode virar Professor se tiver lido 50+ livros
        if (user.getUserType() == UserType.STUDENT && newType == UserType.PROFESSOR) {
            return totalBooksRead >= 50;
        }

        // Comum pode virar Estudante com comprovação
        if (user.getUserType() == UserType.COMMON && newType == UserType.STUDENT) {
            return true; // Requer documento externo
        }

        return false;
    }

    /**
     * Valida se usuário está em situação regular
     */
    public void validateUserStatus(User user, boolean hasPendingFines, int overdueLoans) {
        if (hasPendingFines) {
            throw new BusinessRuleException(
                "Usuário '" + user.getName() + "' possui multas pendentes"
            );
        }

        if (overdueLoans > 0) {
            throw new BusinessRuleException(
                "Usuário '" + user.getName() + "' possui " + overdueLoans + " empréstimos atrasados"
            );
        }
    }
}