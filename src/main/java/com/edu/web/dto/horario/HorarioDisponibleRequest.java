package com.edu.web.dto.horario;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioDisponibleRequest(
        Long doctorId,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        Boolean activo
) {
}
