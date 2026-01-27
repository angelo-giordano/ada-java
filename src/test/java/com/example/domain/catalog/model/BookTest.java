package com.example.domain.catalog.model;

import com.example.domain.shared.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void deveCriarLivroValido() {
        // Arrange & Act
        Book book = new Book(
            BookId.generate(),
            new ISBN("9780132350884"),
            "Clean Code",
            new Author("Robert C. Martin"),
            new Category("Programming"),
            5
        );

        // Assert
        assertNotNull(book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals(5, book.getAvailableQuantity());
        assertTrue(book.isAvailable());
    }

    @Test
    void deveDecrementarEstoque() {
        // Arrange
        Book book = new Book(
            BookId.generate(),
            new ISBN("9780132350884"),
            "Clean Code",
            new Author("Robert C. Martin"),
            new Category("Programming"),
            2
        );

        // Act
        book.decrementStock();

        // Assert
        assertEquals(1, book.getAvailableQuantity());
        assertTrue(book.isAvailable());
    }

    @Test
    void deveLancarExcecaoAoDecrementarEstoqueZero() {
        // Arrange
        Book book = new Book(
            BookId.generate(),
            new ISBN("9780132350884"),
            "Clean Code",
            new Author("Robert C. Martin"),
            new Category("Programming"),
            1
        );
        book.decrementStock(); // Fica com 0

        // Act & Assert
        assertThrows(BusinessRuleException.class, book::decrementStock);
    }

    @Test
    void deveIncrementarEstoque() {
        // Arrange
        Book book = new Book(
            BookId.generate(),
            new ISBN("9780132350884"),
            "Clean Code",
            new Author("Robert C. Martin"),
            new Category("Programming"),
            2
        );
        book.decrementStock(); // Fica com 1

        // Act
        book.incrementStock();

        // Assert
        assertEquals(2, book.getAvailableQuantity());
    }

    @Test
    void deveLancarExcecaoComISBNInvalido() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ISBN("invalid")
        );
    }

    @Test
    void deveAceitarISBN13() {
        ISBN isbn = new ISBN("9780132350884");
        assertTrue(isbn.isISBN13());
        assertFalse(isbn.isISBN10());
    }

    @Test
    void deveAceitarISBN10() {
        ISBN isbn = new ISBN("0132350882");
        assertTrue(isbn.isISBN10());
        assertFalse(isbn.isISBN13());
    }
}