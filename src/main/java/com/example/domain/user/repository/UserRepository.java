package com.example.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.example.domain.user.model.CPF;
import com.example.domain.user.model.Email;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.model.UserType;

/**
 * Repository Interface para User
 */
public interface UserRepository {
    
    /**
     * Salva ou atualiza um usuário
     */
    User save(User user);
    
    /**
     * Busca usuário por ID
     */
    Optional<User> findById(UserId id);
    
    /**
     * Busca usuário por CPF
     */
    Optional<User> findByCpf(CPF cpf);
    
    /**
     * Busca usuário por Email
     */
    Optional<User> findByEmail(Email email);
    
    /**
     * Lista todos os usuários
     */
    List<User> findAll();
    
    /**
     * Busca usuários por nome (busca parcial, case-insensitive)
     */
    List<User> findByName(String name);
    
    /**
     * Busca usuários por tipo
     */
    List<User> findByUserType(UserType userType);
    
    /**
     * Remove um usuário
     */
    void delete(UserId id);
    
    /**
     * Verifica se existe usuário com o CPF
     */
    boolean existsByCpf(CPF cpf);
    
    /**
     * Verifica se existe usuário com o Email
     */
    boolean existsByEmail(Email email);
}