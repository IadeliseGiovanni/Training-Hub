package com.traininghub.identity_service.Service;

import com.traininghub.identity_service.Dto.AuthResponseDto;
import com.traininghub.identity_service.Dto.LoginRequestDto;
import com.traininghub.identity_service.Model.User;
import com.traininghub.identity_service.Repository.UserRepository;
import com.traininghub.identity_service.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponseDto authenticateUser(LoginRequestDto loginRequest) {
        // 1. Esegue l'autenticazione tramite Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Genera il token JWT usando JwtService
        String jwt = jwtService.generateJwtToken(authentication);

        // 3. Recupera l'utente dal database tramite username
        User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // 4. Prende direttamente il ruolo salvato nel campo stringa dell'utente
        String role = user.getRole() != null ? user.getRole() : "USER";

        // 5. Restituisce il DTO con token, username e ruolo per il frontend
        AuthResponseDto response = new AuthResponseDto();
        response.setToken(jwt);
        response.setTokenType("Bearer");
        response.setUsername(user.getUsername());
        response.setRole(role);

        return response;
    }
}