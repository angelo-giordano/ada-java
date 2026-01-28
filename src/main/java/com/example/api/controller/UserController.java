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
import org.springframework.web.bind.annotation.RestController;

import com.example.api.request.RegisterUserRequest;
import com.example.api.response.ErrorResponse;
import com.example.application.catalog.command.RegisterUserCommand;
import com.example.application.dto.UserDTO;
import com.example.application.user.GetUserUseCase;
import com.example.application.user.ListUsersUseCase;
import com.example.application.user.RegisterUserUseCase;
import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.shared.exception.NotFoundException;

/**
 * REST Controller para operações de Usuários
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

    @Autowired
    public UserController(
            RegisterUserUseCase registerUserUseCase,
            GetUserUseCase getUserUseCase,
            ListUsersUseCase listUsersUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        try {
            RegisterUserCommand command = new RegisterUserCommand(
                request.name(),
                request.email(),
                request.cpf(),
                request.userType()
            );
            
            UserDTO result = registerUserUseCase.execute(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (IllegalArgumentException | BusinessRuleException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao cadastrar usuário: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        try {
            UserDTO user = getUserUseCase.execute(id);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<UserDTO> users = listUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }
}