package com.example.application.user;

import com.example.application.catalog.command.RegisterUserCommand;
import com.example.application.dto.UserDTO;
import com.example.domain.shared.event.DomainEventPublisher;
import com.example.domain.shared.exception.BusinessRuleException;
import com.example.domain.user.event.UserRegisteredEvent;
import com.example.domain.user.model.CPF;
import com.example.domain.user.model.Email;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.model.UserType;
import com.example.domain.user.repository.UserRepository;

/**
 * Use Case: Registrar um novo usuário
 */
public class RegisterUserUseCase {
    
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterUserUseCase(UserRepository userRepository, DomainEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public UserDTO execute(RegisterUserCommand command) {
        // Validar se CPF já existe
        CPF cpf = new CPF(command.cpf());
        if (userRepository.existsByCpf(cpf)) {
            throw new BusinessRuleException("Já existe um usuário cadastrado com o CPF: " + cpf.getFormatted());
        }

        // Validar se Email já existe
        Email email = new Email(command.email());
        if (userRepository.existsByEmail(email)) {
            throw new BusinessRuleException("Já existe um usuário cadastrado com o email: " + email.getValue());
        }

        // Criar usuário
        UserType userType = UserType.valueOf(command.userType().toUpperCase());
        User user = new User(
            UserId.generate(),
            command.name(),
            email,
            cpf,
            userType
        );

        // Salvar
        User savedUser = userRepository.save(user);

        // Publicar evento
        eventPublisher.publish(new UserRegisteredEvent(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail().getValue(),
            savedUser.getUserType()
        ));

        // Retornar DTO
        return toDTO(savedUser);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId().getValue(),
            user.getName(),
            user.getEmail().getValue(),
            user.getCpf().getFormatted(),
            user.getUserType().name(),
            user.getUserType().getMaxBooks(),
            user.getUserType().getLoanDays()
        );
    }
}
