package com.traininghub.identity_service.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "L'username o email è obbligatorio")
    private String usernameOrEmail;

    @NotBlank(message = "La password è obbligatoria")
    private String password;
}