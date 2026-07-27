package com.traininghub.identity_service.Mapper;

import com.traininghub.identity_service.Dto.UserRequestDto;
import com.traininghub.identity_service.Dto.UserResponseDto;
import com.traininghub.identity_service.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    public UserResponseDto toResponseDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());

        return dto;
    }
}