package com.edu.web.dto.login;

import com.edu.domain.recurs.rolUsuario;

public record LoginResponse(
        Long id,
        String username,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        rolUsuario rol,
        Boolean activo
) {
}
