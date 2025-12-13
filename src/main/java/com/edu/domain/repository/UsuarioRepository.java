package com.edu.domain.repository;

import com.edu.domain.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Para validar duplicados de username al registrar
    boolean existsByUsernameIgnoreCase(String username);

    // Para validar duplicados de email al registrar
    boolean existsByEmailIgnoreCase(String email);

    // Para login con username
    Optional<UsuarioEntity> findByUsername(String username);

    // Para login con email
    Optional<UsuarioEntity> findByEmail(String email);

    // Para login con username O email
    Optional<UsuarioEntity> findByUsernameOrEmail(String username, String email);

}
