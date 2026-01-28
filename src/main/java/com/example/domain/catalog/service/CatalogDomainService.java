package com.example.domain.catalog.service;

import java.util.List;

import com.example.domain.catalog.model.Book;
import com.example.domain.shared.exception.BusinessRuleException;

/**
 * Domain Service para operações complexas do Catálogo
 * que não pertencem naturalmente a uma única entidade
 */
public class CatalogDomainService {

    /**
     * Verifica se um conjunto de livros pode ser reservado
     * Regra complexa que envolve múltiplos livros
     */
    public boolean canReserveBooks(List<Book> books, int requestedQuantity) {
        if (books == null || books.isEmpty()) {
            return false;
        }

        int totalAvailable = books.stream()
                .mapToInt(Book::getAvailableQuantity)
                .sum();

        return totalAvailable >= requestedQuantity;
    }

    /**
     * Calcula prioridade de aquisição baseado em demanda
     * Exemplo de lógica de domínio que não cabe em Book
     */
    public int calculateAcquisitionPriority(Book book, int activeReservations) {
        if (book.getAvailableQuantity() == 0 && activeReservations > 5) {
            return 10; // Alta prioridade
        } else if (book.getAvailableQuantity() < 2 && activeReservations > 2) {
            return 5; // Média prioridade
        }
        return 1; // Baixa prioridade
    }

    /**
     * Valida se o livro pode ser removido do catálogo
     */
    public void validateBookRemoval(Book book, boolean hasActiveLoans) {
        if (hasActiveLoans) {
            throw new BusinessRuleException(
                "Não é possível remover o livro '" + book.getTitle() + 
                "' pois existem empréstimos ativos"
            );
        }

        if (book.getAvailableQuantity() < book.getTotalQuantity()) {
            throw new BusinessRuleException(
                "Não é possível remover o livro '" + book.getTitle() + 
                "' pois existem exemplares emprestados"
            );
        }
    }
}