package com.edu.application.mapper;

import com.edu.domain.model.EspecialidadEntiity;
import com.edu.web.dto.especialidad.EspecialidadRequest;
import com.edu.web.dto.especialidad.EspecialidadResponse;

public interface EspecialidadMapper {
    EspecialidadEntiity toEntity(EspecialidadRequest request);

    EspecialidadResponse toDto(EspecialidadEntiity especialidad);
}
