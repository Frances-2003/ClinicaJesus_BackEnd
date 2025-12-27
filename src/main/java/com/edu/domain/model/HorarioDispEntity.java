package com.edu.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "horarios_disponibles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_horario_doctor_fecha_hora_inicio",
                        columnNames = {"doctor_id", "fecha", "hora_inicio"}
                )
        }
)

public class HorarioDispEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Doctor al que pertenece este horario
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_horario_doctor")
    )
    private DoctorEntity doctor;

    // Día específico del horario (ej: 2025-12-09)

    @Column(nullable = false)
    private LocalDate fecha;

    // Inicio del bloque de 30 minutos (ej: 10:00)

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    // Fin del bloque (por defecto = horaInicio + 30 minutos)

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
        // Si no se setea horaFin, por defecto es un bloque de 30 minutos
        if (horaInicio != null && horaFin == null) {
            this.horaFin = this.horaInicio.plusMinutes(30);
        }
    }
}
