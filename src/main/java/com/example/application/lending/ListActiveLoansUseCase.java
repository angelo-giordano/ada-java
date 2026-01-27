package com.example.application.lending;

import com.example.domain.lending.model.Loan;
import com.example.domain.lending.repository.LoanRepository;
import com.example.domain.catalog.model.Book;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.repository.UserRepository;
import com.example.application.dto.LoanDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Listar empréstimos ativos de um usuário
 */
public class ListActiveLoansUseCase {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ListActiveLoansUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<LoanDTO> execute(String userId) {
        UserId userIdObj = UserId.of(userId);
        
        User user = userRepository.findById(userIdObj)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Loan> loans = loanRepository.findActiveByUserId(userIdObj);

        return loans.stream()
            .map(loan -> {
                Book book = bookRepository.findById(loan.getBookId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
                return toDTO(loan, user, book);
            })
            .collect(Collectors.toList());
    }

    private LoanDTO toDTO(Loan loan, User user, Book book) {
        double fine = loan.calculateFine(LocalDate.now()).getAmount().doubleValue();
        
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
            fine
        );
    }
}