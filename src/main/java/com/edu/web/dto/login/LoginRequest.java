package com.edu.web.dto.login;

public record LoginRequest(
        String usernameOrEmail,
        String password
) {
}
