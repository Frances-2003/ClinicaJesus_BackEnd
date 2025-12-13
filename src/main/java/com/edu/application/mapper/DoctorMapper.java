package com.edu.application.mapper;

import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.domain.model.UsuarioEntity;
import com.edu.web.dto.doctor.DoctorRequest;
import com.edu.web.dto.doctor.DoctorResponse;

public interface DoctorMapper {

    DoctorEntity toEntity(DoctorRequest request, UsuarioEntity usuario, EspecialidadEntiity especialidad);

    DoctorResponse toDto(DoctorEntity doctor);
}
