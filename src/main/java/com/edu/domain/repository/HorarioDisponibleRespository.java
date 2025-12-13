package com.edu.domain.repository;

import com.edu.domain.model.HorarioDispEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HorarioDisponibleRespository extends JpaRepository<HorarioDispEntity, Long> {

    // Listar horarios activos de un doctor en una fecha específica
    List<HorarioDispEntity> findByDoctorIdAndFechaAndActivoTrue(Long doctorId, LocalDate fecha);

    // Verificar si ya existe un horario para ese doctor, fecha y hora (evita duplicados)
    boolean existsByDoctorIdAndFechaAndHoraInicio(Long doctorId, LocalDate fecha, LocalTime horaInicio);

    // Listar todos los horarios activos de un doctor
    List<HorarioDispEntity> findByDoctorIdAndActivoTrue(Long doctorId);

}
