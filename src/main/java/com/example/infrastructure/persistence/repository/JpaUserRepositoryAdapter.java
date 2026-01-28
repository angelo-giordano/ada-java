package com.example.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.domain.user.model.CPF;
import com.example.domain.user.model.Email;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.model.UserType;
import com.example.domain.user.repository.UserRepository;
import com.example.infrastructure.persistence.entity.UserJpaEntity;

/**
 * Adapter entre Domain Repository e JPA Repository
 */
@Component
public class JpaUserRepositoryAdapter implements UserRepository {
    
    private final UserJpaRepository jpaRepository;

    @Autowired
    public JpaUserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = toEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByCpf(CPF cpf) {
        return jpaRepository.findByCpf(cpf.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByName(String name) {
        return jpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByUserType(UserType userType) {
        UserJpaEntity.UserTypeEnum jpaEnum = UserJpaEntity.UserTypeEnum.valueOf(userType.name());
        return jpaRepository.findByUserType(jpaEnum).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UserId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsByCpf(CPF cpf) {
        return jpaRepository.existsByCpf(cpf.getValue());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.getValue());
    }

    // Mappers

    private UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId().getValue());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail().getValue());
        entity.setCpf(user.getCpf().getValue());
        entity.setUserType(UserJpaEntity.UserTypeEnum.valueOf(user.getUserType().name()));
        return entity;
    }

    private User toDomain(UserJpaEntity entity) {
        return new User(
            UserId.of(entity.getId()),
            entity.getName(),
            new Email(entity.getEmail()),
            new CPF(entity.getCpf()),
            UserType.valueOf(entity.getUserType().name())
        );
    }
}