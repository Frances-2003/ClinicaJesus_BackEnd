package com.edu.domain.repository;

import com.edu.domain.model.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    // Listar solo doctores activos
    List<DoctorEntity> findByActivoTrue();

    // Listar doctores por especialidad (lo usaremos para el combo al elegir cardiología, etc.)
    List<DoctorEntity> findByEspecialidadIdAndActivoTrue(Long especialidadId);

    // Buscar doctor por el usuario asociado (útil si quieres saber si un usuario ya tiene perfil de doctor)
    Optional<DoctorEntity> findByUsuarioId(Long usuarioId);
}
