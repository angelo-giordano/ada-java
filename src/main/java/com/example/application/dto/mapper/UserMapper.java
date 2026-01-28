package com.example.application.dto.mapper;

import com.example.application.dto.UserDTO;
import com.example.domain.user.model.User;

/**
 * Mapper para converter User (domain) <-> UserDTO (application)
 */
public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

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