package com.edu.web.dto.usuario;

public record UsuarioRequest(
        String username,
        String password,
        String nombres,
        String apellidos,
        String email,
        String telefono
) {
}
