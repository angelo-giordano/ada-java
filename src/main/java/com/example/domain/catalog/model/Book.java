package com.example.domain.catalog.model;

import java.util.Objects;

import com.example.domain.shared.exception.BusinessRuleException;

/**
 * Aggregate Root: Book
 * Representa um livro no catálogo
 */
public class Book {
    
    private final BookId id;
    private final ISBN isbn;
    private final String title;
    private final Author author;
    private final Category category;
    private int availableQuantity;
    private final int totalQuantity;

    // Construtor para novo livro
    public Book(BookId id, ISBN isbn, String title, Author author, 
                Category category, int totalQuantity) {
        
        validateTitle(title);
        validateQuantity(totalQuantity);
        
        this.id = id;
        this.isbn = isbn;
        this.title = title.trim();
        this.author = author;
        this.category = category;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
    }

    // Reconstrutor (usado para carregar do banco)
    public Book(BookId id, ISBN isbn, String title, Author author, 
                Category category, int availableQuantity, int totalQuantity) {
        
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.availableQuantity = availableQuantity;
        this.totalQuantity = totalQuantity;
    }

    // Regras de Negócio
    
    public boolean isAvailable() {
        return availableQuantity > 0;
    }

    public void decrementStock() {
        if (!isAvailable()) {
            throw new BusinessRuleException(
                "Livro '" + title + "' não está disponível para empréstimo"
            );
        }
        this.availableQuantity--;
    }

    public void incrementStock() {
        if (availableQuantity >= totalQuantity) {
            throw new BusinessRuleException(
                "Estoque do livro '" + title + "' já está completo"
            );
        }
        this.availableQuantity++;
    }

    // Validações
    
    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser vazio");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Título muito longo (máx 255 caracteres)");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (quantity > 1000) {
            throw new IllegalArgumentException("Quantidade muito grande (máx 1000)");
        }
    }

    // Getters
    
    public BookId getId() {
        return id;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", available=" + availableQuantity + "/" + totalQuantity +
                '}';
    }
}