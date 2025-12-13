package com.edu.application.service.impl;


import com.edu.application.mapper.CitaMapper;
import com.edu.application.service.CitaService;
import com.edu.domain.model.*;
import com.edu.domain.recurs.estadoCita;
import com.edu.domain.recurs.rolUsuario;
import com.edu.domain.repository.CitaRepository;
import com.edu.domain.repository.HorarioDisponibleRespository;
import com.edu.domain.repository.UsuarioRepository;
import com.edu.web.dto.cita.CitaRequest;
import com.edu.web.dto.cita.CitaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {


    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HorarioDisponibleRespository horarioDisponibleRepository;
    private final CitaMapper citaMapper;

    @Override
    public CitaResponse crear(CitaRequest request) {

        // 1. Validar paciente
        UsuarioEntity paciente = usuarioRepository.findById(request.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + request.pacienteId()));

        if (paciente.getRol() != rolUsuario.PACIENTE) {
            throw new RuntimeException("El usuario no tiene rol PACIENTE");
        }

        if (paciente.getActivo() == null || !paciente.getActivo()) {
            throw new RuntimeException("El paciente está inactivo");
        }

        // 2. Validar horario
        HorarioDispEntity horario = horarioDisponibleRepository.findById(request.horarioDisponibleId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + request.horarioDisponibleId()));

        if (horario.getActivo() == null || !horario.getActivo()) {
            throw new RuntimeException("El horario no está activo");
        }

        // 3. Validar que el horario sea futuro (no permitir reservas en pasado)
        LocalDateTime fechaHoraHorario = LocalDateTime.of(horario.getFecha(), horario.getHoraInicio());
        if (fechaHoraHorario.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede reservar una cita en un horario pasado");
        }

        // 4. Validar que no exista otra cita PENDIENTE/CONFIRMADA en ese horario
        boolean existeCitaActiva = citaRepository.existsByHorarioDisponibleIdAndEstadoIn(
                horario.getId(),
                List.of(estadoCita.PENDIENTE, estadoCita.CONFIRMADA)
        );

        if (existeCitaActiva) {
            throw new RuntimeException("El horario ya está reservado");
        }

        // 5. Obtener doctor y precio desde especialidad
        DoctorEntity doctor = horario.getDoctor();
        if (doctor == null) {
            throw new RuntimeException("El horario no tiene un doctor asociado");
        }

        EspecialidadEntiity especialidad = doctor.getEspecialidad();
        if (especialidad == null) {
            throw new RuntimeException("El doctor no tiene una especialidad asociada");
        }

        // 6. Mapear DTO → Entity (paciente + horario)
        CitaEntity cita = citaMapper.toEntity(request, paciente, horario);

        // 7. Setear precio y moneda
        cita.setPrecio(especialidad.getPrecioConsulta());
        cita.setMoneda("PEN"); // por ahora fijo

        // estado y fechaHoraCreacion se setean en @PrePersist de la entidad Cita

        // 8. Guardar
        CitaEntity guardada = citaRepository.save(cita);

        return citaMapper.toDto(guardada);
    }

    @Override
    public List<CitaResponse> listar() {
        return citaRepository.findAll()
                .stream()
                .map(citaMapper::toDto)
                .toList();
    }

    @Override
    public CitaResponse obtenerPorId(Long id) {
        CitaEntity cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));

        return citaMapper.toDto(cita);
    }

    @Override
    public List<CitaResponse> listarPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteIdOrderByFechaHoraCreacionDesc(pacienteId)
                .stream()
                .map(citaMapper::toDto)
                .toList();
    }

    @Override
    public List<CitaResponse> listarPorDoctorYFecha(Long doctorId, LocalDate fecha) {
        return citaRepository
                .findByDoctorIdAndHorarioDisponible_FechaOrderByHorarioDisponible_HoraInicio(doctorId, fecha)
                .stream()
                .map(citaMapper::toDto)
                .toList();
    }

    @Override
    public void cancelar(Long id) {
        CitaEntity cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));

        if (cita.getEstado() == estadoCita.CANCELADA) {
            return; // ya está cancelada
        }

        // (opcional) podrías validar aquí si falta X tiempo mínimo para permitir cancelación

        cita.setEstado(estadoCita.CANCELADA);
        citaRepository.save(cita);
    }

    @Override
    public CitaResponse cambiarEstado(Long id, estadoCita estado) {
        CitaEntity cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Reglas mínimas recomendadas:
        // - No permitir cambios si ya está CANCELADA (opcional)
        if (cita.getEstado() == estadoCita.CANCELADA) {
            throw new RuntimeException("No se puede cambiar el estado de una cita cancelada");
        }

        cita.setEstado(estado);
        CitaEntity guardada = citaRepository.save(cita);

        return citaMapper.toDto(guardada);
    }
}
