package com.edu.web.dto.usuario;

import com.edu.domain.recurs.rolUsuario;

public record UsuarioResponse(
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
