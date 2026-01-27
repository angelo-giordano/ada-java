package com.example.domain.user.model;

import com.example.domain.lending.policy.LoanPolicy;
import com.example.domain.lending.policy.StudentLoanPolicy;
import com.example.domain.lending.policy.ProfessorLoanPolicy;
import com.example.domain.lending.policy.CommonLoanPolicy;
import java.util.Objects;

/**
 * Aggregate Root: User
 * Representa um usuário do sistema
 */
public class User {
    
    private final UserId id;
    private final String name;
    private final Email email;
    private final CPF cpf;
    private final UserType userType;

    public User(UserId id, String name, Email email, CPF cpf, UserType userType) {
        validateName(name);
        
        this.id = id;
        this.name = name.trim();
        this.email = email;
        this.cpf = cpf;
        this.userType = userType;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Nome muito curto (mínimo 3 caracteres)");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Nome muito longo (máximo 255 caracteres)");
        }
    }

    /**
     * Retorna a política de empréstimo baseada no tipo de usuário
     * Exemplo de Strategy Pattern
     */
    public LoanPolicy getLoanPolicy() {
        return switch (userType) {
            case STUDENT -> new StudentLoanPolicy();
            case PROFESSOR -> new ProfessorLoanPolicy();
            case COMMON -> new CommonLoanPolicy();
        };
    }

    // Getters
    
    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public CPF getCpf() {
        return cpf;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", userType=" + userType +
                '}';
    }
}