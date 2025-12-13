package com.edu.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "especialidades",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_especialidad_nombre", columnNames = "nombre")
        }
)
public class EspecialidadEntiity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ej: "Cardiología", "Dermatología"
    @Column(nullable = false, length = 100)
    private String nombre;

    // Precio base de la consulta para esta especialidad
    @Column(name = "precio_consulta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioConsulta;

    @Column(nullable = false)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }

}
