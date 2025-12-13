package com.edu.application.service.impl;

import com.edu.application.mapper.HorarioDisponibleMapper;
import com.edu.application.service.HorarioDisponibleService;
import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.HorarioDispEntity;
import com.edu.domain.repository.DoctorRepository;
import com.edu.domain.repository.HorarioDisponibleRespository;
import com.edu.web.dto.horario.HorarioDisponibleRequest;
import com.edu.web.dto.horario.HorarioDisponibleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    private final HorarioDisponibleRespository horarioDisponibleRepository;
    private final DoctorRepository doctorRepository;
    private final HorarioDisponibleMapper horarioDisponibleMapper;


    @Override
    public HorarioDisponibleResponse crear(HorarioDisponibleRequest request) {

        DoctorEntity doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con id: " + request.doctorId()));

        LocalDate fecha = request.fecha();
        LocalTime horaInicio = request.horaInicio();
        LocalTime horaFin = request.horaFin();

        if (fecha == null || horaInicio == null) {
            throw new RuntimeException("La fecha y la hora de inicio son obligatorias");
        }

        // Validar bloque de 30 minutos si viene horaFin
        if (horaFin != null && !horaFin.equals(horaInicio.plusMinutes(30))) {
            throw new RuntimeException("El horario debe ser de 30 minutos (horaFin = horaInicio + 30 minutos)");
        }

        // Validar que no exista ya un horario con ese doctor/fecha/horaInicio
        boolean existe = horarioDisponibleRepository
                .existsByDoctorIdAndFechaAndHoraInicio(doctor.getId(), fecha, horaInicio);

        if (existe) {
            throw new RuntimeException("Ya existe un horario para ese doctor, fecha y hora de inicio");
        }

        HorarioDispEntity horario = horarioDisponibleMapper.toEntity(request, doctor);
        HorarioDispEntity guardado = horarioDisponibleRepository.save(horario);

        return horarioDisponibleMapper.toDto(guardado);
    }

    @Override
    public List<HorarioDisponibleResponse> listar() {
        return horarioDisponibleRepository.findAll()
                .stream()
                .map(horarioDisponibleMapper::toDto)
                .toList();
    }

    @Override
    public List<HorarioDisponibleResponse> listarPorDoctorYFecha(Long doctorId, LocalDate fecha) {
        return horarioDisponibleRepository.findByDoctorIdAndFechaAndActivoTrue(doctorId, fecha)
                .stream()
                .map(horarioDisponibleMapper::toDto)
                .toList();
    }

    @Override
    public HorarioDisponibleResponse obtenerPorId(Long id) {
        HorarioDispEntity horario = horarioDisponibleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));

        return horarioDisponibleMapper.toDto(horario);
    }

    @Override
    public HorarioDisponibleResponse actualizar(Long id, HorarioDisponibleRequest request) {

        HorarioDispEntity horario = horarioDisponibleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));

        // Si se envía un nuevo doctorId, cambiarlo
        if (request.doctorId() != null && !request.doctorId().equals(horario.getDoctor().getId())) {
            DoctorEntity nuevoDoctor = doctorRepository.findById(request.doctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor no encontrado con id: " + request.doctorId()));
            horario.setDoctor(nuevoDoctor);
        }

        LocalDate nuevaFecha = request.fecha() != null ? request.fecha() : horario.getFecha();
        LocalTime nuevaHoraInicio = request.horaInicio() != null ? request.horaInicio() : horario.getHoraInicio();
        LocalTime nuevaHoraFin = request.horaFin() != null ? request.horaFin() : horario.getHoraFin();

        // Validar bloque de 30 minutos si viene horaFin en la actualización
        if (nuevaHoraFin != null && !nuevaHoraFin.equals(nuevaHoraInicio.plusMinutes(30))) {
            throw new RuntimeException("El horario debe ser de 30 minutos (horaFin = horaInicio + 30 minutos)");
        }

        // Validar duplicado solo si cambian doctor/fecha/horaInicio
        boolean cambiaClaveHorario =
                !nuevaFecha.equals(horario.getFecha()) ||
                        !nuevaHoraInicio.equals(horario.getHoraInicio()) ||
                        !horario.getDoctor().getId().equals(
                                request.doctorId() != null ? request.doctorId() : horario.getDoctor().getId()
                        );

        if (cambiaClaveHorario) {
            boolean existe = horarioDisponibleRepository
                    .existsByDoctorIdAndFechaAndHoraInicio(horario.getDoctor().getId(), nuevaFecha, nuevaHoraInicio);

            if (existe) {
                throw new RuntimeException("Ya existe un horario para ese doctor, fecha y hora de inicio");
            }
        }

        horario.setFecha(nuevaFecha);
        horario.setHoraInicio(nuevaHoraInicio);
        horario.setHoraFin(nuevaHoraFin);

        if (request.activo() != null) {
            horario.setActivo(request.activo());
        }

        HorarioDispEntity actualizado = horarioDisponibleRepository.save(horario);
        return horarioDisponibleMapper.toDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        HorarioDispEntity horario = horarioDisponibleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));

        // Eliminación lógica
        horario.setActivo(false);
        horarioDisponibleRepository.save(horario);
    }
}
