package com.example.infrastructure.persistence.repository;

import com.example.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository para UserJpaEntity
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    
    Optional<UserJpaEntity> findByCpf(String cpf);
    
    Optional<UserJpaEntity> findByEmail(String email);
    
    List<UserJpaEntity> findByNameContainingIgnoreCase(String name);
    
    List<UserJpaEntity> findByUserType(UserJpaEntity.UserTypeEnum userType);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByEmail(String email);
}