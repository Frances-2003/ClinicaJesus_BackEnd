package com.edu.web.controller;


import com.edu.application.service.HorarioDisponibleService;
import com.edu.web.dto.horario.HorarioDisponibleRequest;
import com.edu.web.dto.horario.HorarioDisponibleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioDisponibleController {


    private final HorarioDisponibleService horarioDisponibleService;

    // CREAR HORARIO
    @PostMapping
    public ResponseEntity<HorarioDisponibleResponse> crear(@RequestBody HorarioDisponibleRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(horarioDisponibleService.crear(request));
    }

    // LISTAR TODOS LOS HORARIOS
    @GetMapping
    public ResponseEntity<List<HorarioDisponibleResponse>> listar() {
        return ResponseEntity.ok(horarioDisponibleService.listar());
    }

    // LISTAR HORARIOS POR DOCTOR Y FECHA (para mostrar slots disponibles)
    // /api/horarios/por-doctor-y-fecha?doctorId=3&fecha=2025-12-10
    @GetMapping("/por-doctor-y-fecha")
    public ResponseEntity<List<HorarioDisponibleResponse>> listarPorDoctorYFecha(
            @RequestParam Long doctorId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return ResponseEntity.ok(horarioDisponibleService.listarPorDoctorYFecha(doctorId, fecha));
    }

    // OBTENER HORARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDisponibleResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioDisponibleService.obtenerPorId(id));
    }

    // ACTUALIZAR HORARIO
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDisponibleResponse> actualizar(
            @PathVariable Long id,
            @RequestBody HorarioDisponibleRequest request
    ) {
        return ResponseEntity.ok(horarioDisponibleService.actualizar(id, request));
    }

    // ELIMINAR (LÓGICO: activo = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horarioDisponibleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
