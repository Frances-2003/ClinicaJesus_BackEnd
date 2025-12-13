package com.edu.web.dto.especialidad;

import java.math.BigDecimal;

public record EspecialidadResponse(
        Long id,
        String nombre,
        BigDecimal precioConsulta,
        Boolean activo
) {
}
