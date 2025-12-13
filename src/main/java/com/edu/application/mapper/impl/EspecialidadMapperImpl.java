package com.edu.application.mapper.impl;


import com.edu.application.mapper.EspecialidadMapper;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.web.dto.especialidad.EspecialidadRequest;
import com.edu.web.dto.especialidad.EspecialidadResponse;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapperImpl implements EspecialidadMapper {
    @Override
    public EspecialidadEntiity toEntity(EspecialidadRequest request) {
        if (request == null) {
            return null;
        }

        return EspecialidadEntiity.builder()
                .nombre(request.nombre())
                .precioConsulta(request.precioConsulta())
                // Si viene null, lo dejamos que lo complete el @PrePersist de la entidad (true por defecto)
                .activo(request.activo())
                .build();
    }

    @Override
    public EspecialidadResponse toDto(EspecialidadEntiity especialidad) {
        if (especialidad == null) {
            return null;
        }

        return new EspecialidadResponse(
                especialidad.getId(),
                especialidad.getNombre(),
                especialidad.getPrecioConsulta(),
                especialidad.getActivo()
        );
    }
}
