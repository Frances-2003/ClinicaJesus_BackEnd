package com.edu.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctores")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1:1 con Usuario (este usuario debe tener rol = DOCTOR)
    @OneToOne(optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_doctor_usuario")
    )
    private UsuarioEntity usuario;

    // Especialidad del doctor: Cardiología, Dermatología, etc.
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "especialidad_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_doctor_especialidad")
    )
    private EspecialidadEntiity especialidad;

    // Número de colegiatura médica (CMP en Perú), puede ser único si quieres
    @Column(name = "numero_cmp", length = 20)
    private String numeroCmp;

    @Column(nullable = false)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }

}
