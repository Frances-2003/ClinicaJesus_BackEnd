package com.edu.application.service;

import com.edu.web.dto.doctor.DoctorRequest;
import com.edu.web.dto.doctor.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse crear(DoctorRequest request);

    List<DoctorResponse> listar();

    List<DoctorResponse> listarPorEspecialidad(Long especialidadId);

    DoctorResponse obtenerPorId(Long id);

    DoctorResponse actualizar(Long id, DoctorRequest request);

    void eliminar(Long id);

    List<DoctorResponse> listar1();
}
