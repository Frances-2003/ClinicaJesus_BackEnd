package com.edu.application.mapper.impl;


import com.edu.application.mapper.HorarioDisponibleMapper;
import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.domain.model.HorarioDispEntity;
import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.horario.HorarioDisponibleRequest;
import com.edu.web.dto.horario.HorarioDisponibleResponse;
import org.springframework.stereotype.Component;

@Component
public class HorarioDisponibleMapperImpl implements HorarioDisponibleMapper {
    @Override
    public HorarioDispEntity toEntity(HorarioDisponibleRequest request, DoctorEntity doctor) {
        if (request == null || doctor == null) {
            return null;
        }

        return HorarioDispEntity.builder()
                .doctor(doctor)
                .fecha(request.fecha())
                .horaInicio(request.horaInicio())
                // si viene null, la entidad lo completará con +30 min en @PrePersist
                .horaFin(request.horaFin())
                .activo(request.activo())
                .build();
    }

    @Override
    public HorarioDisponibleResponse toDto(HorarioDispEntity horario) {
        if (horario == null) {
            return null;
        }

        DoctorEntity doctor = horario.getDoctor();
        UsuarioEntity usuario = doctor != null ? doctor.getUsuario() : null;
        EspecialidadEntiity especialidad = doctor != null ? doctor.getEspecialidad() : null;

        Long doctorId = doctor != null ? doctor.getId() : null;

        String doctorNombreCompleto = null;
        if (usuario != null) {
            String nombres = usuario.getNombres() != null ? usuario.getNombres() : "";
            String apellidos = usuario.getApellidos() != null ? usuario.getApellidos() : "";
            doctorNombreCompleto = (nombres + " " + apellidos).trim();
        }

        String especialidadNombre = especialidad != null ? especialidad.getNombre() : null;

        return new HorarioDisponibleResponse(
                horario.getId(),
                doctorId,
                doctorNombreCompleto,
                especialidadNombre,
                horario.getFecha(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.getActivo()
        );
    }
}
