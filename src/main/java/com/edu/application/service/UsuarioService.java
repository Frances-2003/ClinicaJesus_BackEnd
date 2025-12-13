package com.edu.application.service;

import com.edu.web.dto.login.LoginRequest;
import com.edu.web.dto.login.LoginResponse;
import com.edu.web.dto.usuario.UsuarioRequest;
import com.edu.web.dto.usuario.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse registrar( UsuarioRequest request);

    List<UsuarioResponse> listar();

    UsuarioResponse obtenerPorId(Long id);

    UsuarioResponse actualizar(Long id, UsuarioRequest request);

    void eliminar(Long id); // eliminación lógica (activo = false)

    LoginResponse login(LoginRequest request);
}
