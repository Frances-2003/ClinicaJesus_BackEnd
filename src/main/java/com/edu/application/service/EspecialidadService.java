package com.edu.application.service;

import com.edu.web.dto.especialidad.EspecialidadRequest;
import com.edu.web.dto.especialidad.EspecialidadResponse;

import java.util.List;

public interface EspecialidadService {

    EspecialidadResponse crear(EspecialidadRequest request);

    List<EspecialidadResponse> listar();

    List<EspecialidadResponse> listarActivas();

    EspecialidadResponse obtenerPorId(Long id);

    EspecialidadResponse actualizar(Long id, EspecialidadRequest request);

    void eliminar(Long id);
}
