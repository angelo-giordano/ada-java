package com.example.domain.lending.model;

import com.example.domain.catalog.model.BookId;
import com.example.domain.user.model.UserId;
import com.example.domain.shared.valueobject.Money;
import com.example.domain.shared.exception.BusinessRuleException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Aggregate Root: Loan
 * Representa um empréstimo de livro
 */
public class Loan {
    
    private static final double FINE_PER_DAY = 2.0;
    
    private final LoanId id;
    private final UserId userId;
    private final BookId bookId;
    private final LocalDate borrowedAt;
    private final LocalDate dueDate;
    private LoanStatus status;
    private LocalDate returnedAt;

    // Construtor para novo empréstimo
    public Loan(LoanId id, UserId userId, BookId bookId, 
                LocalDate borrowedAt, LocalDate dueDate) {
        
        validateDates(borrowedAt, dueDate);
        
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.status = LoanStatus.ACTIVE;
        this.returnedAt = null;
    }

    // Reconstrutor (carregar do banco)
    public Loan(LoanId id, UserId userId, BookId bookId, 
                LocalDate borrowedAt, LocalDate dueDate, 
                LoanStatus status, LocalDate returnedAt) {
        
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.status = status;
        this.returnedAt = returnedAt;
    }

    // Regras de Negócio
    
    public void returnBook(LocalDate returnDate) {
        if (this.status == LoanStatus.RETURNED) {
            throw new BusinessRuleException("Livro já foi devolvido");
        }
        
        if (returnDate.isBefore(borrowedAt)) {
            throw new BusinessRuleException(
                "Data de devolução não pode ser anterior à data de empréstimo"
            );
        }
        
        this.returnedAt = returnDate;
        this.status = LoanStatus.RETURNED;
    }

    public Money calculateFine(LocalDate referenceDate) {
        if (!isOverdue(referenceDate)) {
            return Money.zero();
        }
        
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, referenceDate);
        return Money.of(daysOverdue * FINE_PER_DAY);
    }

    public boolean isOverdue(LocalDate referenceDate) {
        return status == LoanStatus.ACTIVE && referenceDate.isAfter(dueDate);
    }

    public boolean isActive() {
        return status == LoanStatus.ACTIVE;
    }

    public long getDaysUntilDue(LocalDate referenceDate) {
        if (!isActive()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(referenceDate, dueDate);
    }

    // Validações
    
    private void validateDates(LocalDate borrowedAt, LocalDate dueDate) {
        if (borrowedAt == null || dueDate == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        
        if (borrowedAt.isAfter(dueDate)) {
            throw new IllegalArgumentException(
                "Data de empréstimo não pode ser posterior à data de devolução"
            );
        }
        
        if (ChronoUnit.DAYS.between(borrowedAt, dueDate) > 90) {
            throw new IllegalArgumentException(
                "Período de empréstimo não pode exceder 90 dias"
            );
        }
    }

    // Getters
    
    public LoanId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public LocalDate getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }
}