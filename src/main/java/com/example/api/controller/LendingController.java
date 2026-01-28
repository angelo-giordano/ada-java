package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.request.BorrowBookRequest;
import com.example.api.response.ErrorResponse;
import com.example.application.catalog.command.BorrowBookCommand;
import com.example.application.catalog.command.ReturnBookCommand;
import com.example.application.dto.LoanDTO;
import com.example.application.lending.BorrowBookUseCase;
import com.example.application.lending.ListActiveLoansUseCase;
import com.example.application.lending.ReturnBookUseCase;
import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.shared.exception.NotFoundException;

/**
 * REST Controller para operações de Empréstimos
 */
@RestController
@RequestMapping("/api/lending")
@CrossOrigin(origins = "*")
public class LendingController {

    private final BorrowBookUseCase borrowBookUseCase;
    private final ReturnBookUseCase returnBookUseCase;
    private final ListActiveLoansUseCase listActiveLoansUseCase;

    @Autowired
    public LendingController(
            BorrowBookUseCase borrowBookUseCase,
            ReturnBookUseCase returnBookUseCase,
            ListActiveLoansUseCase listActiveLoansUseCase) {
        this.borrowBookUseCase = borrowBookUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.listActiveLoansUseCase = listActiveLoansUseCase;
    }

    @PostMapping("/loans")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowBookRequest request) {
        try {
            BorrowBookCommand command = new BorrowBookCommand(
                request.userId(),
                request.bookId()
            );
            
            LoanDTO result = borrowBookUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao realizar empréstimo: " + e.getMessage()));
        }
    }

    @PutMapping("/loans/{loanId}/return")
    public ResponseEntity<?> returnBook(@PathVariable String loanId) {
        try {
            ReturnBookCommand command = new ReturnBookCommand(loanId);
            LoanDTO result = returnBookUseCase.execute(command);
            return ResponseEntity.ok(result);
            
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao devolver livro: " + e.getMessage()));
        }
    }

    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<?> listActiveLoans(@PathVariable String userId) {
        try {
            List<LoanDTO> loans = listActiveLoansUseCase.execute(userId);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao listar empréstimos: " + e.getMessage()));
        }
    }
}