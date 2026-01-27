package com.example.config;

import com.example.application.catalog.*;
import com.example.application.lending.*;
import com.example.application.user.*;
import com.example.domain.catalog.repository.BookRepository;
import com.example.domain.lending.repository.LoanRepository;
import com.example.domain.user.repository.UserRepository;
import com.example.domain.shared.event.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de Injeção de Dependências
 * Conecta Domain -> Application -> Infrastructure
 */
@Configuration
public class ApplicationConfig {

    // ============================================
    // CATALOG USE CASES
    // ============================================

    @Bean
    public RegisterBookUseCase registerBookUseCase(
            BookRepository bookRepository,
            DomainEventPublisher eventPublisher) {
        return new RegisterBookUseCase(bookRepository, eventPublisher);
    }

    @Bean
    public GetBookUseCase getBookUseCase(BookRepository bookRepository) {
        return new GetBookUseCase(bookRepository);
    }

    @Bean
    public ListBooksUseCase listBooksUseCase(BookRepository bookRepository) {
        return new ListBooksUseCase(bookRepository);
    }

    @Bean
    public SearchBooksUseCase searchBooksUseCase(BookRepository bookRepository) {
        return new SearchBooksUseCase(bookRepository);
    }

    // ============================================
    // USER USE CASES
    // ============================================

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            DomainEventPublisher eventPublisher) {
        return new RegisterUserUseCase(userRepository, eventPublisher);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserRepository userRepository) {
        return new ListUsersUseCase(userRepository);
    }

    // ============================================
    // LENDING USE CASES
    // ============================================

    @Bean
    public BorrowBookUseCase borrowBookUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            DomainEventPublisher eventPublisher) {
        return new BorrowBookUseCase(
            loanRepository,
            bookRepository,
            userRepository,
            eventPublisher
        );
    }

    @Bean
    public ReturnBookUseCase returnBookUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            DomainEventPublisher eventPublisher) {
        return new ReturnBookUseCase(
            loanRepository,
            bookRepository,
            userRepository,
            eventPublisher
        );
    }

    @Bean
    public ListActiveLoansUseCase listActiveLoansUseCase(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {
        return new ListActiveLoansUseCase(
            loanRepository,
            bookRepository,
            userRepository
        );
    }
}