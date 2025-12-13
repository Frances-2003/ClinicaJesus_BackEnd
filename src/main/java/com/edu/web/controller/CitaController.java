package com.edu.web.controller;


import com.edu.application.service.CitaService;
import com.edu.web.dto.cita.CitaRequest;
import com.edu.web.dto.cita.CitaResponse;
import com.edu.web.dto.cita.EstadoCitaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    // CREAR CITA (RESERVAR)
    @PostMapping
    public ResponseEntity<CitaResponse> crear(@RequestBody CitaRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(citaService.crear(request));
    }

    // LISTAR TODAS LAS CITAS (para admin / debug)
    @GetMapping
    public ResponseEntity<List<CitaResponse>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    // OBTENER CITA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    // LISTAR CITAS POR PACIENTE (MIS CITAS)
    // /api/citas/por-paciente?pacienteId=10
    @GetMapping("/por-paciente")
    public ResponseEntity<List<CitaResponse>> listarPorPaciente(
            @RequestParam Long pacienteId
    ) {
        return ResponseEntity.ok(citaService.listarPorPaciente(pacienteId));
    }

    // LISTAR CITAS POR DOCTOR Y FECHA (AGENDA DEL DOCTOR)
    // /api/citas/por-doctor-y-fecha?doctorId=3&fecha=2025-12-10
    @GetMapping("/por-doctor-y-fecha")
    public ResponseEntity<List<CitaResponse>> listarPorDoctorYFecha(
            @RequestParam Long doctorId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return ResponseEntity.ok(citaService.listarPorDoctorYFecha(doctorId, fecha));
    }

    // CANCELAR CITA (NO BORRAR, SOLO CAMBIAR ESTADO A CANCELADA)
    // POST /api/citas/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody EstadoCitaRequest request
    ) {

        return ResponseEntity.ok(citaService.cambiarEstado(id, request.estado()));
    }
}
