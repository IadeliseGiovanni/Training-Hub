package com.traininghub.identity_service.Controller;

import com.traininghub.identity_service.Dto.AuthResponseDto;
import com.traininghub.identity_service.Dto.LoginRequestDto;
import com.traininghub.identity_service.Dto.UserRequestDto;
import com.traininghub.identity_service.Dto.UserResponseDto;
import com.traininghub.identity_service.Service.AuthService;
import com.traininghub.identity_service.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // Endpoint per il Login: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto) {
        try {
            AuthResponseDto response = authService.authenticateUser(requestDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Stampa l'errore esatto nel terminale Java per capire la causa del 403
            e.printStackTrace();
            return ResponseEntity.status(401).body("Errore di autenticazione: " + e.getMessage());
        }
    }

    // Endpoint per la Registrazione: POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(createdUser);
    }
}