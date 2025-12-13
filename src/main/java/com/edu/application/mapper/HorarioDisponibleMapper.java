package com.edu.application.mapper;

import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.HorarioDispEntity;
import com.edu.web.dto.horario.HorarioDisponibleRequest;
import com.edu.web.dto.horario.HorarioDisponibleResponse;

public interface HorarioDisponibleMapper {

    HorarioDispEntity toEntity(HorarioDisponibleRequest request, DoctorEntity doctor);

    HorarioDisponibleResponse toDto(HorarioDispEntity horario);
}
