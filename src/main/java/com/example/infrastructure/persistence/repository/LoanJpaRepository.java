package com.example.infrastructure.persistence.repository;

import com.example.infrastructure.persistence.entity.LoanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA Repository para LoanJpaEntity
 */
@Repository
public interface LoanJpaRepository extends JpaRepository<LoanJpaEntity, String> {
    
    List<LoanJpaEntity> findByUserIdAndStatus(String userId, LoanJpaEntity.LoanStatusEnum status);
    
    List<LoanJpaEntity> findByUserId(String userId);
    
    List<LoanJpaEntity> findByBookId(String bookId);
    
    List<LoanJpaEntity> findByStatus(LoanJpaEntity.LoanStatusEnum status);
    
    @Query("SELECT l FROM LoanJpaEntity l WHERE l.status = 'ACTIVE' AND l.dueDate < :referenceDate")
    List<LoanJpaEntity> findOverdue(@Param("referenceDate") LocalDate referenceDate);
    
    @Query("SELECT l FROM LoanJpaEntity l WHERE l.status = 'ACTIVE' AND l.dueDate BETWEEN :start AND :end")
    List<LoanJpaEntity> findDueSoon(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT COUNT(l) FROM LoanJpaEntity l WHERE l.userId = :userId AND l.status = 'ACTIVE'")
    int countActiveByUserId(@Param("userId") String userId);
}