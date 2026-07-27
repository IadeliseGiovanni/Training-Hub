package com.traininghub.identity_service.Service;

import com.traininghub.identity_service.Dto.AuthResponseDto;
import com.traininghub.identity_service.Dto.LoginRequestDto;
import com.traininghub.identity_service.Dto.UserRequestDto;
import com.traininghub.identity_service.Dto.UserResponseDto;
import com.traininghub.identity_service.Exception.ResourceNotFoundException;
import com.traininghub.identity_service.Mapper.UserMapper;
import com.traininghub.identity_service.Model.User;
import com.traininghub.identity_service.Repository.UserRepository;
import com.traininghub.identity_service.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("Username già in uso");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email già in uso");
        }

        // Cifriamo la password prima di mappare e salvare
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = userMapper.toEntity(requestDto);
        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginDto) {
        // Cerchiamo l'utente sia per username che per email
        User user = userRepository.findByUsername(loginDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginDto.getUsernameOrEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("Credenziali non valide")));

        // Verifichiamo la password inserita con quella cifrata nel DB
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenziali non valide");
        }

        // Generiamo il token JWT
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return new AuthResponseDto(token);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));

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
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));

        existingUser.setUsername(requestDto.getUsername());
        existingUser.setEmail(requestDto.getEmail());

        // Cifriamo la nuova password se è stata fornita
        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        existingUser.setRole(requestDto.getRole());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utente non trovato con id: " + id);
        }
        userRepository.deleteById(id);
    }
}