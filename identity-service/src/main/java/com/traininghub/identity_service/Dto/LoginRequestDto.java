package com.traininghub.identity_service.Dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String usernameOrEmail;
    private String password;
}