package com.edu.application.mapper.impl;

import com.edu.application.mapper.DoctorMapper;
import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.doctor.DoctorRequest;
import com.edu.web.dto.doctor.DoctorResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DoctorMapperImpl implements DoctorMapper {


    @Override
    public DoctorEntity toEntity(DoctorRequest request, UsuarioEntity usuario, EspecialidadEntiity especialidad) {
        if (request == null || usuario == null || especialidad == null) {
            return null;
        }

        return DoctorEntity.builder()
                .usuario(usuario)
                .especialidad(especialidad)
                .numeroCmp(request.numeroCmp())
                .activo(request.activo())
                .build();
    }

    @Override
    public DoctorResponse toDto(DoctorEntity doctor) {
        if (doctor == null) {
            return null;
        }

        UsuarioEntity usuario = doctor.getUsuario();
        EspecialidadEntiity especialidad = doctor.getEspecialidad();

        String nombres = usuario != null ? usuario.getNombres() : null;
        String apellidos = usuario != null ? usuario.getApellidos() : null;
        String email = usuario != null ? usuario.getEmail() : null;

        Long usuarioId = usuario != null ? usuario.getId() : null;

        Long especialidadId = especialidad != null ? especialidad.getId() : null;
        String especialidadNombre = especialidad != null ? especialidad.getNombre() : null;
        BigDecimal especialidadPrecio = especialidad != null ? especialidad.getPrecioConsulta() : null;

        return new DoctorResponse(
                doctor.getId(),
                usuarioId,
                nombres,
                apellidos,
                email,
                especialidadId,
                especialidadNombre,
                especialidadPrecio,
                doctor.getNumeroCmp(),
                doctor.getActivo()
        );
    }
}
