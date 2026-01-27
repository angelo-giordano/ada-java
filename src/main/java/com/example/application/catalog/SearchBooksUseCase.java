package com.example.application.catalog;

import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.repository.BookRepository;
import com.example.application.dto.BookDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Buscar livros por título ou autor
 */
public class SearchBooksUseCase {
    
    private final BookRepository bookRepository;

    public SearchBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> execute(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        // Busca por título e autor, depois combina os resultados
        List<Book> byTitle = bookRepository.findByTitle(query);
        List<Book> byAuthor = bookRepository.findByAuthor(query);

        return byTitle.stream()
            .collect(Collectors.toSet()) // Remove duplicatas
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