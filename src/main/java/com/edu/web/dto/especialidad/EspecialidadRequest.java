package com.edu.web.dto.especialidad;

import java.math.BigDecimal;

public record EspecialidadRequest(
        String nombre,
        BigDecimal precioConsulta,
        Boolean activo
) {
}
