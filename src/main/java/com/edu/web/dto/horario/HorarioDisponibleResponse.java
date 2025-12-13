package com.edu.web.dto.horario;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioDisponibleResponse(
        Long id,
        Long doctorId,
        String doctorNombreCompleto,
        String especialidadNombre,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        Boolean activo
) {
}
