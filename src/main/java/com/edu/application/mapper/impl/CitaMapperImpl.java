package com.edu.application.mapper.impl;

import com.edu.application.mapper.CitaMapper;
import com.edu.domain.model.*;
import com.edu.web.dto.cita.CitaRequest;
import com.edu.web.dto.cita.CitaResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class CitaMapperImpl implements CitaMapper {
    @Override
    public CitaEntity toEntity(CitaRequest request, UsuarioEntity paciente, HorarioDispEntity horarioDisponible) {
        if (request == null || paciente == null || horarioDisponible == null) {
            return null;
        }

        DoctorEntity doctor = horarioDisponible.getDoctor();

        return CitaEntity.builder()
                .paciente(paciente)
                .doctor(doctor)
                .horarioDisponible(horarioDisponible)
                .motivoConsulta(request.motivoConsulta())
                // precio, moneda, estado, fechaHoraCreacion los setea el service o @PrePersist
                .build();
    }

    @Override
    public CitaResponse toDto(CitaEntity cita) {
        if (cita == null) {
            return null;
        }

        UsuarioEntity paciente = cita.getPaciente();
        DoctorEntity doctor = cita.getDoctor();
        HorarioDispEntity horario = cita.getHorarioDisponible();
        EspecialidadEntiity especialidad = (doctor != null) ? doctor.getEspecialidad() : null;

        // Paciente
        Long pacienteId = (paciente != null) ? paciente.getId() : null;
        String pacienteNombreCompleto = null;
        if (paciente != null) {
            String nombres = paciente.getNombres() != null ? paciente.getNombres() : "";
            String apellidos = paciente.getApellidos() != null ? paciente.getApellidos() : "";
            pacienteNombreCompleto = (nombres + " " + apellidos).trim();
        }

        // Doctor
        Long doctorId = (doctor != null) ? doctor.getId() : null;
        String doctorNombreCompleto = null;
        if (doctor != null && doctor.getUsuario() != null) {
            UsuarioEntity usuarioDoctor = doctor.getUsuario();
            String nombres = usuarioDoctor.getNombres() != null ? usuarioDoctor.getNombres() : "";
            String apellidos = usuarioDoctor.getApellidos() != null ? usuarioDoctor.getApellidos() : "";
            doctorNombreCompleto = (nombres + " " + apellidos).trim();
        }

        // Especialidad
        Long especialidadId = (especialidad != null) ? especialidad.getId() : null;
        String especialidadNombre = (especialidad != null) ? especialidad.getNombre() : null;

        // Horario
        Long horarioId = (horario != null) ? horario.getId() : null;
        LocalDate fecha = (horario != null) ? horario.getFecha() : null;
        LocalTime horaInicio = (horario != null) ? horario.getHoraInicio() : null;
        LocalTime horaFin = (horario != null) ? horario.getHoraFin() : null;

        BigDecimal precio = cita.getPrecio();
        String moneda = cita.getMoneda();

        return new CitaResponse(
                cita.getId(),
                pacienteId,
                pacienteNombreCompleto,
                doctorId,
                doctorNombreCompleto,
                especialidadId,
                especialidadNombre,
                precio,
                moneda,
                horarioId,
                fecha,
                horaInicio,
                horaFin,
                cita.getEstado(),
                cita.getMotivoConsulta(),
                cita.getObservaciones(),
                cita.getFechaHoraCreacion()
        );
    }
}
