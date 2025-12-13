package com.edu.web.dto.cita;

public record CitaRequest(
        Long pacienteId,
        Long horarioDisponibleId,
        String motivoConsulta
) {
}
