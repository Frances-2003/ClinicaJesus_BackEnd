package com.edu.application.service;

import com.edu.domain.recurs.estadoCita;
import com.edu.web.dto.cita.CitaRequest;
import com.edu.web.dto.cita.CitaResponse;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {

    CitaResponse crear(CitaRequest request);

    List<CitaResponse> listar();

    CitaResponse obtenerPorId(Long id);

    List<CitaResponse> listarPorPaciente(Long pacienteId);

    List<CitaResponse> listarPorDoctorYFecha(Long doctorId, LocalDate fecha);

    void cancelar(Long id);

    CitaResponse cambiarEstado(Long id, estadoCita estado);
}
