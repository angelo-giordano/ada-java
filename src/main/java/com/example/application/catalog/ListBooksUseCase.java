package com.example.application.catalog;

import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.repository.BookRepository;
import com.example.application.dto.BookDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Listar todos os livros
 */
public class ListBooksUseCase {
    
    private final BookRepository bookRepository;

    public ListBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> execute() {
        return bookRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<BookDTO> executeAvailableOnly() {
        return bookRepository.findAvailable()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
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