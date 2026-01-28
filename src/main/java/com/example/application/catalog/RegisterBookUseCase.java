package com.example.application.catalog;

import com.example.application.catalog.command.RegisterBookCommand;
import com.example.application.dto.BookDTO;
import com.example.domain.catalog.event.BookRegisteredEvent;
import com.example.domain.catalog.model.Author;
import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.model.BookId;
import com.example.domain.catalog.model.Category;
import com.example.domain.catalog.model.ISBN;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.shared.event.DomainEventPublisher;
import com.example.domain.shared.exception.BusinessRuleException;

/**
 * Use Case: Registrar um novo livro no catálogo
 */
public class RegisterBookUseCase {
    
    private final BookRepository bookRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterBookUseCase(BookRepository bookRepository, DomainEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    public BookDTO execute(RegisterBookCommand command) {
        // Validar se ISBN já existe
        ISBN isbn = new ISBN(command.isbn());
        if (bookRepository.existsByIsbn(isbn)) {
            throw new BusinessRuleException("Já existe um livro cadastrado com o ISBN: " + command.isbn());
        }

        // Criar o livro
        Book book = new Book(
            BookId.generate(),
            isbn,
            command.title(),
            new Author(command.authorName()),
            new Category(command.category()),
            command.totalQuantity()
        );

        // Salvar
        Book savedBook = bookRepository.save(book);

        // Publicar evento
        eventPublisher.publish(new BookRegisteredEvent(
            savedBook.getId(),
            savedBook.getTitle(),
            savedBook.getIsbn().getValue()
        ));

        // Retornar DTO
        return toDTO(savedBook);
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
