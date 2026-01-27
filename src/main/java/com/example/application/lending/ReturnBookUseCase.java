package com.example.application.lending;

import java.time.LocalDate;

import com.example.application.catalog.command.ReturnBookCommand;
import com.example.application.dto.LoanDTO;
import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.lending.event.BookReturnedEvent;
import com.example.domain.lending.model.Loan;
import com.example.domain.lending.model.LoanId;
import com.example.domain.lending.repository.LoanRepository;
import com.example.domain.shared.event.DomainEventPublisher;
import com.example.domain.shared.exception.NotFoundException;
import com.example.domain.shared.valueobject.Money;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.UserRepository;

/**
 * Use Case: Devolver um livro
 */
public class ReturnBookUseCase {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    public ReturnBookUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            DomainEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public LoanDTO execute(ReturnBookCommand command) {
        // 1. Buscar empréstimo
        LoanId loanId = LoanId.of(command.loanId());
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> NotFoundException.forEntity("Empréstimo", command.loanId()));

        // 2. Buscar livro
        Book book = bookRepository.findById(loan.getBookId())
            .orElseThrow(() -> NotFoundException.forEntity("Livro", loan.getBookId().getValue()));

        // 3. Buscar usuário (para DTO)
        User user = userRepository.findById(loan.getUserId())
            .orElseThrow(() -> NotFoundException.forEntity("Usuário", loan.getUserId().getValue()));

        // 4. Data de devolução
        LocalDate returnDate = LocalDate.now();

        // 5. Calcular multa
        Money fine = loan.calculateFine(returnDate);

        // 6. Devolver livro (atualiza status)
        loan.returnBook(returnDate);

        // 7. Incrementar estoque do livro
        book.incrementStock();

        // 8. Salvar
        Loan returnedLoan = loanRepository.save(loan);
        bookRepository.save(book);

        // 9. Publicar evento
        eventPublisher.publish(new BookReturnedEvent(
            loan.getId(),
            loan.getBookId(),
            returnDate,
            fine
        ));

        // 10. Retornar DTO
        return toDTO(returnedLoan, user, book, fine);
    }

    private LoanDTO toDTO(Loan loan, User user, Book book, Money fine) {
        return new LoanDTO(
            loan.getId().getValue(),
            user.getId().getValue(),
            user.getName(),
            book.getId().getValue(),
            book.getTitle(),
            loan.getBorrowedAt(),
            loan.getDueDate(),
            loan.getReturnedAt(),
            loan.getStatus().name(),
            fine.getAmount().doubleValue()
        );
    }
}