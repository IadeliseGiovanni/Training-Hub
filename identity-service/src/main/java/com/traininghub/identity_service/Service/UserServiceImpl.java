package com.traininghub.identity_service.Service;

import com.traininghub.identity_service.Dto.UserRequestDto;
import com.traininghub.identity_service.Dto.UserResponseDto;
import com.traininghub.identity_service.Mapper.UserMapper;
import com.traininghub.identity_service.Model.User;
import com.traininghub.identity_service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("Username già in uso");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email già in uso");
        }

        User user = userMapper.toEntity(requestDto);
        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));

        return userMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));

        existingUser.setUsername(requestDto.getUsername());
        existingUser.setEmail(requestDto.getEmail());
        existingUser.setPassword(requestDto.getPassword());
        existingUser.setRole(requestDto.getRole());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utente non trovato con id: " + id);
        }
        userRepository.deleteById(id);
    }
}