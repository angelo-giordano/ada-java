package com.example.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.domain.catalog.model.BookId;
import com.example.domain.lending.model.Loan;
import com.example.domain.lending.model.LoanId;
import com.example.domain.lending.model.LoanStatus;
import com.example.domain.lending.repository.LoanRepository;
import com.example.domain.user.model.UserId;
import com.example.infrastructure.persistence.entity.LoanJpaEntity;

/**
 * Adapter entre Domain Repository e JPA Repository
 */
@Component
public class JpaLoanRepositoryAdapter implements LoanRepository {
    
    private final LoanJpaRepository jpaRepository;

    @Autowired
    public JpaLoanRepositoryAdapter(LoanJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Loan save(Loan loan) {
        LoanJpaEntity entity = toEntity(loan);
        LoanJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<Loan> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findActiveByUserId(UserId userId) {
        return jpaRepository.findByUserIdAndStatus(userId.getValue(), LoanJpaEntity.LoanStatusEnum.ACTIVE)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByBookId(BookId bookId) {
        return jpaRepository.findByBookId(bookId.getValue()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByStatus(LoanStatus status) {
        LoanJpaEntity.LoanStatusEnum jpaStatus = LoanJpaEntity.LoanStatusEnum.valueOf(status.name());
        return jpaRepository.findByStatus(jpaStatus).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findOverdue(LocalDate referenceDate) {
        return jpaRepository.findOverdue(referenceDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findDueSoon(LocalDate referenceDate, int days) {
        LocalDate endDate = referenceDate.plusDays(days);
        return jpaRepository.findDueSoon(referenceDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveByUserId(UserId userId) {
        return jpaRepository.countActiveByUserId(userId.getValue());
    }

    @Override
    public void delete(LoanId id) {
        jpaRepository.deleteById(id.getValue());
    }

    // Mappers

    private LoanJpaEntity toEntity(Loan loan) {
        LoanJpaEntity entity = new LoanJpaEntity();
        entity.setId(loan.getId().getValue());
        entity.setUserId(loan.getUserId().getValue());
        entity.setBookId(loan.getBookId().getValue());
        entity.setBorrowedAt(loan.getBorrowedAt());
        entity.setDueDate(loan.getDueDate());
        entity.setReturnedAt(loan.getReturnedAt());
        entity.setStatus(LoanJpaEntity.LoanStatusEnum.valueOf(loan.getStatus().name()));
        return entity;
    }

    private Loan toDomain(LoanJpaEntity entity) {
        return new Loan(
            LoanId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            BookId.of(entity.getBookId()),
            entity.getBorrowedAt(),
            entity.getDueDate(),
            LoanStatus.valueOf(entity.getStatus().name()),
            entity.getReturnedAt()
        );
    }
}