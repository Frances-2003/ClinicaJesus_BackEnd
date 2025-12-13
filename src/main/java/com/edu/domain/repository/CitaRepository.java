package com.edu.domain.repository;

import com.edu.domain.model.CitaEntity;
import com.edu.domain.recurs.estadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CitaRepository extends JpaRepository<CitaEntity, Long> {
    // Listar citas de un paciente (útil para "Mis citas")
    List<CitaEntity> findByPacienteIdOrderByFechaHoraCreacionDesc(Long pacienteId);

    // Listar citas de un doctor en una fecha específica (agenda del doctor)
    List<CitaEntity> findByDoctorIdAndHorarioDisponible_FechaOrderByHorarioDisponible_HoraInicio(
            Long doctorId,
            LocalDate fecha
    );

    // Ver si ya existe una cita ACTIVA en un horario concreto
    //    (para evitar doble reserva en el mismo slot)
    boolean existsByHorarioDisponibleIdAndEstadoIn(
            Long horarioDisponibleId,
            Collection<estadoCita> estados
    );

    // Todas las citas de un doctor con un estado específico (PENDIENTE, CONFIRMADA, etc.)
    List<CitaEntity> findByDoctorIdAndEstado(Long doctorId, estadoCita estado);

    // Todas las citas de un paciente con un estado específico
    List<CitaEntity> findByPacienteIdAndEstado(Long pacienteId, estadoCita estado);

    // Buscar todas las citas asociadas a un horario específico (por si quieres auditar)
    List<CitaEntity> findByHorarioDisponibleId(Long horarioDisponibleId);
}

