package com.edu.web.dto.doctor;

import java.math.BigDecimal;

public record DoctorResponse(
        Long id,
        Long usuarioId,
        String nombres,
        String apellidos,
        String email,
        Long especialidadId,
        String especialidadNombre,
        BigDecimal especialidadPrecioConsulta,
        String numeroCmp,
        Boolean activo
) {
}
