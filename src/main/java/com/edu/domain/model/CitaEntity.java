package com.edu.domain.model;

import com.edu.domain.recurs.estadoCita;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "citas")
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Paciente que reserva la cita (Usuario con rol = PACIENTE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "paciente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cita_paciente")
    )
    private UsuarioEntity paciente;

    // Doctor que atenderá la cita
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cita_doctor")
    )
    private DoctorEntity doctor;

    // Slot de horario que se está ocupando
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "horario_disponible_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cita_horario_disponible")
    )
    private HorarioDispEntity horarioDisponible;

    // Precio final de la consulta (copiado desde la especialidad al crear la cita)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // Moneda de la cita, ej: "PEN"
    @Column(length = 3, nullable = false)
    private String moneda;

    // Motivo de la consulta (por qué viene el paciente)
    @Column(name = "motivo_consulta", nullable = false, length = 500)
    private String motivoConsulta;

    // Observaciones posteriores (ej: notas del doctor)
    @Column(length = 1000)
    private String observaciones;

    // Estado de la cita: PENDIENTE, CONFIRMADA, CANCELADA
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private estadoCita estado;

    // Cuándo se creó la cita
    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fechaHoraCreacion;

    @PrePersist
    public void prePersist() {
        if (fechaHoraCreacion == null) {
            fechaHoraCreacion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = estadoCita.PENDIENTE;
        }
        if (moneda == null) {
            moneda = "PEN";
        }
    }
}
