package com.traininghub.identity_service.Controller;

import com.traininghub.identity_service.Dto.AuthResponseDto;
import com.traininghub.identity_service.Dto.LoginRequestDto;
import com.traininghub.identity_service.Dto.UserRequestDto;
import com.traininghub.identity_service.Dto.UserResponseDto;
import com.traininghub.identity_service.Service.AuthService;
import com.traininghub.identity_service.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permette le chiamate dal frontend Angular
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Endpoint per il Login: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        AuthResponseDto response = authService.authenticateUser(requestDto);
        return ResponseEntity.ok(response);
    }

    // Endpoint per la Registrazione: POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        // Chiama il metodo del service che si occupa di salvare l'utente (es. createUser o registerUser)
        // Se il tuo metodo nel UserService si chiama createUser, usa quello:
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(createdUser);
    }
}