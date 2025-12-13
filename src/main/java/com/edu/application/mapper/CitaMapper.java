package com.edu.application.mapper;

import com.edu.domain.model.CitaEntity;
import com.edu.domain.model.HorarioDispEntity;
import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.cita.CitaRequest;
import com.edu.web.dto.cita.CitaResponse;

public interface CitaMapper {
    CitaEntity toEntity(CitaRequest request, UsuarioEntity paciente, HorarioDispEntity horarioDisponible);

    CitaResponse toDto(CitaEntity cita);
}
