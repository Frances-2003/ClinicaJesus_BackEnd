package com.edu.web.dto.login;

public record RegistroRequestDto(
        String username,
        String password,
        String nombres,
        String apellidos,
        String email,
        String telefono
) {
}
