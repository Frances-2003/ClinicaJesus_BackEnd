package com.edu.domain.repository;

import com.edu.domain.model.EspecialidadEntiity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntiity, Long> {

    // Listar solo especialidades activas (útil para combos en el front)

    List<EspecialidadEntiity> findByActivoTrue();

    // Validar que no se repita el nombre de la especialidad (Cardiología, etc.)
    boolean existsByNombreIgnoreCase(String nombre);
}
