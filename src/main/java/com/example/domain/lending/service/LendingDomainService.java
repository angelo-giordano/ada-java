package com.example.domain.lending.service;

import java.time.LocalDate;
import java.util.List;

import com.example.domain.lending.model.Loan;
import com.example.domain.lending.model.LoanStatus;
import com.example.domain.shared.valueobject.Money;

/**
 * Domain Service para lógica de negócio de Empréstimos
 * que não pertence a uma única entidade
 */
public class LendingDomainService {

    /**
     * Calcula multa total de múltiplos empréstimos
     */
    public Money calculateTotalFines(List<Loan> loans, LocalDate referenceDate) {
        return loans.stream()
                .map(loan -> loan.calculateFine(referenceDate))
                .reduce(Money.zero(), Money::add);
    }

    /**
     * Verifica se usuário tem multas pendentes acima do limite
     */
    public boolean hasExcessiveFines(List<Loan> loans, LocalDate referenceDate, Money limit) {
        Money totalFines = calculateTotalFines(loans, referenceDate);
        return totalFines.isGreaterThan(limit);
    }

    /**
     * Identifica empréstimos que precisam de atenção
     */
    public List<Loan> findLoansNeedingAttention(List<Loan> loans, LocalDate referenceDate) {
        return loans.stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .filter(loan -> {
                    long daysUntilDue = loan.getDaysUntilDue(referenceDate);
                    return daysUntilDue <= 3 || daysUntilDue < 0; // Vence em 3 dias ou atrasado
                })
                .toList();
    }

    /**
     * Valida se renovação de empréstimo é permitida
     */
    public boolean canRenewLoan(Loan loan, int maxRenewals, int currentRenewals) {
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            return false;
        }

        if (currentRenewals >= maxRenewals) {
            return false;
        }

        // Não pode renovar se já está atrasado
        return !loan.isOverdue(LocalDate.now());
    }
}