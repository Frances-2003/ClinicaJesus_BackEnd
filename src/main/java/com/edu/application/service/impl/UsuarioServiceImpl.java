package com.edu.application.service.impl;

import com.edu.application.mapper.UsuarioMapper;
import com.edu.application.service.UsuarioService;
import com.edu.domain.model.UsuarioEntity;
import com.edu.domain.recurs.rolUsuario;
import com.edu.domain.repository.UsuarioRepository;
import com.edu.web.dto.login.LoginRequest;
import com.edu.web.dto.login.LoginResponse;
import com.edu.web.dto.usuario.UsuarioRequest;
import com.edu.web.dto.usuario.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder; // necesitas un @Bean en config

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {

        // Validar username/email únicos
        if (usuarioRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new RuntimeException("El username ya está en uso: " + request.username());
        }

        if (usuarioRepository.existsByEmailIgnoreCase(request.email())) {
            throw new RuntimeException("El email ya está en uso: " + request.email());
        }

        // Mapear DTO → Entity
        UsuarioEntity usuario = usuarioMapper.toEntity(request);

        // Encriptar password
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Rol por defecto: PACIENTE
        usuario.setRol(rolUsuario.PACIENTE);

        // Activo por defecto (si no se setea en prePersist)
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        UsuarioEntity guardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(guardado);
    }

    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public UsuarioResponse obtenerPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Actualizar username (validando duplicado)
        if (request.username() != null && !request.username().equalsIgnoreCase(usuario.getUsername())) {
            if (usuarioRepository.existsByUsernameIgnoreCase(request.username())) {
                throw new RuntimeException("El username ya está en uso: " + request.username());
            }
            usuario.setUsername(request.username());
        }

        // Actualizar email (validando duplicado)
        if (request.email() != null && !request.email().equalsIgnoreCase(usuario.getEmail())) {
            if (usuarioRepository.existsByEmailIgnoreCase(request.email())) {
                throw new RuntimeException("El email ya está en uso: " + request.email());
            }
            usuario.setEmail(request.email());
        }

        if (request.nombres() != null) {
            usuario.setNombres(request.nombres());
        }

        if (request.apellidos() != null) {
            usuario.setApellidos(request.apellidos());
        }

        if (request.telefono() != null) {
            usuario.setTelefono(request.telefono());
        }

        // Si se manda una nueva contraseña, se encripta
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }

        UsuarioEntity actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setActivo(false); // eliminación lógica
        usuarioRepository.save(usuario);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        UsuarioEntity usuario = usuarioRepository
                .findByUsernameOrEmail(request.usernameOrEmail(), request.usernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            throw new RuntimeException("El usuario está inactivo");
        }

        boolean passwordOk = passwordEncoder.matches(request.password(), usuario.getPassword());
        if (!passwordOk) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.getActivo()
        );
    }

    @Override
    public UsuarioResponse cambiarRol(Long id, rolUsuario rol) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setRol(rol);
        UsuarioEntity actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(actualizado);
    }

}
