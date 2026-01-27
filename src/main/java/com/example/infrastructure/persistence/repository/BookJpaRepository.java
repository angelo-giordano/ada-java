package com.example.infrastructure.persistence.repository;

import com.example.infrastructure.persistence.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository para BookJpaEntity
 */
@Repository
public interface BookJpaRepository extends JpaRepository<BookJpaEntity, String> {
    
    Optional<BookJpaEntity> findByIsbn(String isbn);
    
    List<BookJpaEntity> findByTitleContainingIgnoreCase(String title);
    
    List<BookJpaEntity> findByAuthorNameContainingIgnoreCase(String authorName);
    
    List<BookJpaEntity> findByCategoryContainingIgnoreCase(String category);
    
    @Query("SELECT b FROM BookJpaEntity b WHERE b.availableQuantity > 0")
    List<BookJpaEntity> findAvailable();
    
    boolean existsByIsbn(String isbn);
}