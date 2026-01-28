package com.example.domain.lending.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.domain.shared.valueobject.Money;

/**
 * Domain Service especializado em cálculo de multas
 */
public class FineCalculator {

    private static final double FINE_PER_DAY = 2.0;
    private static final double MAX_FINE_PERCENTAGE = 0.5; // 50% do valor do livro
    private static final double AVERAGE_BOOK_VALUE = 50.0;

    /**
     * Calcula multa com teto máximo
     */
    public Money calculateFineWithCap(LocalDate dueDate, LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate)) {
            return Money.zero();
        }

        long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
        double calculatedFine = daysOverdue * FINE_PER_DAY;
        double maxFine = AVERAGE_BOOK_VALUE * MAX_FINE_PERCENTAGE;

        double finalFine = Math.min(calculatedFine, maxFine);
        return Money.of(finalFine);
    }

    /**
     * Calcula multa progressiva (aumenta após certo período)
     */
    public Money calculateProgressiveFine(LocalDate dueDate, LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate)) {
            return Money.zero();
        }

        long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
        double fine;

        if (daysOverdue <= 7) {
            // Primeira semana: R$ 2,00/dia
            fine = daysOverdue * 2.0;
        } else if (daysOverdue <= 14) {
            // Segunda semana: R$ 3,00/dia
            fine = (7 * 2.0) + ((daysOverdue - 7) * 3.0);
        } else {
            // Após 14 dias: R$ 5,00/dia
            fine = (7 * 2.0) + (7 * 3.0) + ((daysOverdue - 14) * 5.0);
        }

        return Money.of(fine);
    }

    /**
     * Aplica desconto para devolução antecipada (exemplo de regra de negócio)
     */
    public Money applyEarlyReturnDiscount(Money originalFine, LocalDate returnDate, LocalDate dueDate) {
        long daysEarly = ChronoUnit.DAYS.between(returnDate, dueDate);
        
        if (daysEarly >= 7) {
            // Devolução com 7+ dias de antecedência: sem multa futura
            return Money.zero();
        }

        return originalFine;
    }
}