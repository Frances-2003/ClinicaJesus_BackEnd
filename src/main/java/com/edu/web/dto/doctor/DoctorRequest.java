package com.edu.web.dto.doctor;

public record DoctorRequest(
        Long usuarioId,
        Long especialidadId,
        String numeroCmp,
        Boolean activo
) {
}
