package com.example.application.user;

import com.example.domain.user.model.User;
import com.example.domain.user.model.UserId;
import com.example.domain.user.repository.UserRepository;
import com.example.domain.shared.exception.NotFoundException;
import com.example.application.dto.UserDTO;

/**
 * Use Case: Buscar usuário por ID
 */
public class GetUserUseCase {
    
    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(String userId) {
        User user = userRepository.findById(UserId.of(userId))
            .orElseThrow(() -> NotFoundException.forEntity("Usuário", userId));

        return toDTO(user);
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