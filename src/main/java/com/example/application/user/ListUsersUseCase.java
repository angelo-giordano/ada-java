package com.example.application.user;

import com.example.domain.user.model.User;
import com.example.domain.user.repository.UserRepository;
import com.example.application.dto.UserDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Listar todos os usuários
 */
public class ListUsersUseCase {
    
    private final UserRepository userRepository;

    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> execute() {
        return userRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
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