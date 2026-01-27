package com.example.domain.catalog.repository;

import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.model.BookId;
import com.example.domain.catalog.model.ISBN;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface para Book
 * Define o contrato de persistência (DIP - Dependency Inversion Principle)
 */
public interface BookRepository {
    
    /**
     * Salva ou atualiza um livro
     */
    Book save(Book book);
    
    /**
     * Busca livro por ID
     */
    Optional<Book> findById(BookId id);
    
    /**
     * Busca livro por ISBN
     */
    Optional<Book> findByIsbn(ISBN isbn);
    
    /**
     * Lista todos os livros
     */
    List<Book> findAll();
    
    /**
     * Busca livros por título (busca parcial, case-insensitive)
     */
    List<Book> findByTitle(String title);
    
    /**
     * Busca livros por autor (busca parcial, case-insensitive)
     */
    List<Book> findByAuthor(String authorName);
    
    /**
     * Busca livros por categoria
     */
    List<Book> findByCategory(String categoryName);
    
    /**
     * Lista apenas livros disponíveis (quantity > 0)
     */
    List<Book> findAvailable();
    
    /**
     * Remove um livro
     */
    void delete(BookId id);
    
    /**
     * Verifica se existe livro com o ISBN
     */
    boolean existsByIsbn(ISBN isbn);
}