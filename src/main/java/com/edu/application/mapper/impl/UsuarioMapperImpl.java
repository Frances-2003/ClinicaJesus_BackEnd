package com.edu.application.mapper.impl;

import com.edu.application.mapper.UsuarioMapper;
import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.usuario.UsuarioRequest;
import com.edu.web.dto.usuario.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioEntity toEntity(UsuarioRequest request) {
        if (request == null) {
            return null;
        }

        return UsuarioEntity.builder()
                .username(request.username())
                .password(request.password())
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .email(request.email())
                .telefono(request.telefono())
                .build();
    }

    @Override
    public UsuarioResponse toDto(UsuarioEntity usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(
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
}
