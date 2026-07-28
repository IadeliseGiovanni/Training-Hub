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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(requestDto.getRole());

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginDto.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("Utente non trovato")));

        String token = jwtService.generateJwtToken(authentication);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));

        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        user.setRole(requestDto.getRole());

        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));
        userRepository.delete(user);
    }
}