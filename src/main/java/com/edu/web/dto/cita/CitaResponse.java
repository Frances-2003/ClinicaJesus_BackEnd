package com.edu.web.dto.cita;

import com.edu.domain.recurs.estadoCita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CitaResponse(
        Long id,
        Long pacienteId,
        String pacienteNombreCompleto,
        Long doctorId,
        String doctorNombreCompleto,
        Long especialidadId,
        String especialidadNombre,
        BigDecimal precio,
        String moneda,
        Long horarioDisponibleId,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        estadoCita estado,
        String motivoConsulta,
        String observaciones,
        LocalDateTime fechaHoraCreacion
) {
}
