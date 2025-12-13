package com.edu.application.mapper;

import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.usuario.UsuarioRequest;
import com.edu.web.dto.usuario.UsuarioResponse;

public interface UsuarioMapper {
    UsuarioEntity toEntity(UsuarioRequest request);

    UsuarioResponse toDto(UsuarioEntity usuario);
}
