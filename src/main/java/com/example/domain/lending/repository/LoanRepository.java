package com.example.domain.lending.repository;

import com.example.domain.lending.model.Loan;
import com.example.domain.lending.model.LoanId;
import com.example.domain.lending.model.LoanStatus;
import com.example.domain.user.model.UserId;
import com.example.domain.catalog.model.BookId;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface para Loan
 */
public interface LoanRepository {
    
    /**
     * Salva ou atualiza um empréstimo
     */
    Loan save(Loan loan);
    
    /**
     * Busca empréstimo por ID
     */
    Optional<Loan> findById(LoanId id);
    
    /**
     * Lista todos os empréstimos
     */
    List<Loan> findAll();
    
    /**
     * Busca empréstimos ativos de um usuário
     */
    List<Loan> findActiveByUserId(UserId userId);
    
    /**
     * Busca todos os empréstimos de um usuário (ativos e histórico)
     */
    List<Loan> findByUserId(UserId userId);
    
    /**
     * Busca empréstimos por livro
     */
    List<Loan> findByBookId(BookId bookId);
    
    /**
     * Busca empréstimos por status
     */
    List<Loan> findByStatus(LoanStatus status);
    
    /**
     * Busca empréstimos atrasados (ativos e com due_date < hoje)
     */
    List<Loan> findOverdue(LocalDate referenceDate);
    
    /**
     * Busca empréstimos que vencem em breve (próximos N dias)
     */
    List<Loan> findDueSoon(LocalDate referenceDate, int days);
    
    /**
     * Conta quantos empréstimos ativos o usuário tem
     */
    int countActiveByUserId(UserId userId);
    
    /**
     * Remove um empréstimo
     */
    void delete(LoanId id);
}