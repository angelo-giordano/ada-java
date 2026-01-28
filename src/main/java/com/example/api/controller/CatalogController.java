package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.request.RegisterBookRequest;
import com.example.api.response.ErrorResponse;
import com.example.application.catalog.GetBookUseCase;
import com.example.application.catalog.ListBooksUseCase;
import com.example.application.catalog.RegisterBookUseCase;
import com.example.application.catalog.SearchBooksUseCase;
import com.example.application.catalog.command.RegisterBookCommand;
import com.example.application.dto.BookDTO;
import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.shared.exception.NotFoundException;

/**
 * REST Controller para operações de Catálogo
 */
@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    private final RegisterBookUseCase registerBookUseCase;
    private final GetBookUseCase getBookUseCase;
    private final ListBooksUseCase listBooksUseCase;
    private final SearchBooksUseCase searchBooksUseCase;

    @Autowired
    public CatalogController(
            RegisterBookUseCase registerBookUseCase,
            GetBookUseCase getBookUseCase,
            ListBooksUseCase listBooksUseCase,
            SearchBooksUseCase searchBooksUseCase) {
        this.registerBookUseCase = registerBookUseCase;
        this.getBookUseCase = getBookUseCase;
        this.listBooksUseCase = listBooksUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
    }

    @PostMapping("/books")
    public ResponseEntity<?> registerBook(@RequestBody RegisterBookRequest request) {
        try {
            RegisterBookCommand command = new RegisterBookCommand(
                request.isbn(),
                request.title(),
                request.authorName(),
                request.category(),
                request.totalQuantity()
            );
            
            BookDTO result = registerBookUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (IllegalArgumentException | BusinessRuleException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao cadastrar livro: " + e.getMessage()));
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable String id) {
        try {
            BookDTO book = getBookUseCase.execute(id);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> listBooks(
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly) {
        
        List<BookDTO> books = availableOnly 
            ? listBooksUseCase.executeAvailableOnly()
            : listBooksUseCase.execute();
            
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String q) {
        List<BookDTO> books = searchBooksUseCase.execute(q);
        return ResponseEntity.ok(books);
    }
}