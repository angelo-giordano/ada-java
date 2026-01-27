package com.example.infrastructure.persistence.repository;

import com.example.domain.catalog.model.*;
import com.example.domain.catalog.repository.BookRepository;
import com.example.infrastructure.persistence.entity.BookJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter entre Domain Repository e JPA Repository
 * Converte Domain Models <-> JPA Entities
 */
@Component
public class JpaBookRepositoryAdapter implements BookRepository {
    
    private final BookJpaRepository jpaRepository;

    @Autowired
    public JpaBookRepositoryAdapter(BookJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Book save(Book book) {
        BookJpaEntity entity = toEntity(book);
        BookJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Book> findById(BookId id) {
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<Book> findByIsbn(ISBN isbn) {
        return jpaRepository.findByIsbn(isbn.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title) {
        return jpaRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String authorName) {
        return jpaRepository.findByAuthorNameContainingIgnoreCase(authorName).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByCategory(String categoryName) {
        return jpaRepository.findByCategoryContainingIgnoreCase(categoryName).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAvailable() {
        return jpaRepository.findAvailable().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(BookId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsByIsbn(ISBN isbn) {
        return jpaRepository.existsByIsbn(isbn.getValue());
    }

    // Mappers: Domain <-> JPA Entity

    private BookJpaEntity toEntity(Book book) {
        BookJpaEntity entity = new BookJpaEntity();
        entity.setId(book.getId().getValue());
        entity.setIsbn(book.getIsbn().getValue());
        entity.setTitle(book.getTitle());
        entity.setAuthorName(book.getAuthor().getName());
        entity.setCategory(book.getCategory().getName());
        entity.setAvailableQuantity(book.getAvailableQuantity());
        entity.setTotalQuantity(book.getTotalQuantity());
        return entity;
    }

    private Book toDomain(BookJpaEntity entity) {
        return new Book(
            BookId.of(entity.getId()),
            new ISBN(entity.getIsbn()),
            entity.getTitle(),
            new Author(entity.getAuthorName()),
            new Category(entity.getCategory()),
            entity.getAvailableQuantity(),
            entity.getTotalQuantity()
        );
    }
}