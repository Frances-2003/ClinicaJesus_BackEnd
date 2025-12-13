package com.edu.application.service;

import com.edu.web.dto.horario.HorarioDisponibleRequest;
import com.edu.web.dto.horario.HorarioDisponibleResponse;

import java.time.LocalDate;
import java.util.List;

public interface HorarioDisponibleService {

    HorarioDisponibleResponse crear(HorarioDisponibleRequest request);

    List<HorarioDisponibleResponse> listar();

    List<HorarioDisponibleResponse> listarPorDoctorYFecha(Long doctorId, LocalDate fecha);

    HorarioDisponibleResponse obtenerPorId(Long id);

    HorarioDisponibleResponse actualizar(Long id, HorarioDisponibleRequest request);

    void eliminar(Long id);
}
