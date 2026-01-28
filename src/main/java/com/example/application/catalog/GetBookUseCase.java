package com.example.application.catalog;

import com.example.application.dto.BookDTO;
import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.model.BookId;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.shared.exception.NotFoundException;

/**
 * Use Case: Buscar um livro por ID
 */
public class GetBookUseCase {
    
    private final BookRepository bookRepository;

    public GetBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO execute(String bookId) {
        Book book = bookRepository.findById(BookId.of(bookId))
            .orElseThrow(() -> NotFoundException.forEntity("Livro", bookId));

        return toDTO(book);
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
            book.getId().getValue(),
            book.getIsbn().getValue(),
            book.getTitle(),
            book.getAuthor().getName(),
            book.getCategory().getName(),
            book.getAvailableQuantity(),
            book.getTotalQuantity()
        );
    }
}