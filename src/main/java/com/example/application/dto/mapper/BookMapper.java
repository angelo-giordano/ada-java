package com.example.application.dto.mapper;

import com.example.application.dto.BookDTO;
import com.example.domain.catalog.model.Book;

/**
 * Mapper para converter Book (domain) <-> BookDTO (application)
 */
public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

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

    // Nota: Não fazemos toDomain aqui porque Book é criado via construtor
    // com validações. A conversão é feita nos Use Cases.
}