package com.example.application.lending;

import com.example.domain.lending.model.*;
import com.example.domain.lending.repository.LoanRepository;
import com.example.domain.lending.policy.LoanPolicy;
import com.example.domain.lending.event.LoanCreatedEvent;
import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.model.BookId;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.repository.UserRepository;
import com.example.domain.shared.event.DomainEventPublisher;
import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.shared.exception.NotFoundException;
import com.example.application.dto.LoanDTO;
import com.example.application.catalog.command.BorrowBookCommand;
import java.time.LocalDate;
import java.util.List;

/**
 * Use Case: Emprestar um livro
 * Orquestra múltiplos aggregates e bounded contexts
 */
public class BorrowBookUseCase {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    public BorrowBookUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            DomainEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public LoanDTO execute(BorrowBookCommand command) {
        // 1. Buscar usuário
        UserId userId = UserId.of(command.userId());
        User user = userRepository.findById(userId)
            .orElseThrow(() -> NotFoundException.forEntity("Usuário", command.userId()));

        // 2. Buscar livro
        BookId bookId = BookId.of(command.bookId());
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> NotFoundException.forEntity("Livro", command.bookId()));

        // 3. Verificar disponibilidade do livro
        if (!book.isAvailable()) {
            throw new BusinessRuleException(
                "Livro '" + book.getTitle() + "' não está disponível para empréstimo"
            );
        }

        // 4. Obter política de empréstimo do usuário
        LoanPolicy policy = user.getLoanPolicy();

        // 5. Verificar limite de empréstimos ativos
        int activeLoans = loanRepository.countActiveByUserId(userId);
        if (!policy.canBorrow(user, activeLoans)) {
            throw new BusinessRuleException(
                "Usuário '" + user.getName() + "' atingiu o limite de " + 
                policy.getMaxBooks() + " empréstimos simultâneos"
            );
        }

        // 6. Calcular datas
        LocalDate borrowedAt = LocalDate.now();
        LocalDate dueDate = borrowedAt.plusDays(policy.getLoanDays());

        // 7. Criar empréstimo
        Loan loan = new Loan(
            LoanId.generate(),
            userId,
            bookId,
            borrowedAt,
            dueDate
        );

        // 8. Decrementar estoque do livro
        book.decrementStock();

        // 9. Salvar
        Loan savedLoan = loanRepository.save(loan);
        bookRepository.save(book);

        // 10. Publicar evento
        eventPublisher.publish(new LoanCreatedEvent(
            savedLoan.getId(),
            savedLoan.getBookId(),
            savedLoan.getUserId(),
            savedLoan.getDueDate()
        ));

        // 11. Retornar DTO
        return toDTO(savedLoan, user, book);
    }

    private LoanDTO toDTO(Loan loan, User user, Book book) {
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
            0.0
        );
    }
}