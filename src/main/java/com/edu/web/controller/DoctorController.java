package com.edu.web.controller;


import com.edu.application.service.DoctorService;
import com.edu.web.dto.doctor.DoctorRequest;
import com.edu.web.dto.doctor.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // CREAR
    @PostMapping
    public ResponseEntity<DoctorResponse> crear(@RequestBody DoctorRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(doctorService.crear(request));
    }

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> listar() {
        return ResponseEntity.ok(doctorService.listar());
    }

    // LISTAR POR ESPECIALIDAD (para el combo dinámico)
    // /api/doctores/por-especialidad?especialidadId=1
    @GetMapping("/por-especialidad")
    public ResponseEntity<List<DoctorResponse>> listarPorEspecialidad(
            @RequestParam Long especialidadId
    ) {
        return ResponseEntity.ok(doctorService.listarPorEspecialidad(especialidadId));
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.obtenerPorId(id));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> actualizar(
            @PathVariable Long id,
            @RequestBody DoctorRequest request
    ) {
        return ResponseEntity.ok(doctorService.actualizar(id, request));
    }

    // ELIMINAR (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        doctorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
