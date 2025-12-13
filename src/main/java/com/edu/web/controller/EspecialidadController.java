package com.edu.web.controller;

import com.edu.application.service.EspecialidadService;
import com.edu.web.dto.especialidad.EspecialidadRequest;
import com.edu.web.dto.especialidad.EspecialidadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    // CREAR
    @PostMapping
    public ResponseEntity<EspecialidadResponse> crear(@RequestBody EspecialidadRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(especialidadService.crear(request));
    }

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<EspecialidadResponse>> listar() {
        return ResponseEntity.ok(especialidadService.listar());
    }

    // LISTAR SOLO ACTIVAS (para combos)
    @GetMapping("/activas")
    public ResponseEntity<List<EspecialidadResponse>> listarActivas() {
        return ResponseEntity.ok(especialidadService.listarActivas());
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.obtenerPorId(id));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponse> actualizar(
            @PathVariable Long id,
            @RequestBody EspecialidadRequest request
    ) {
        return ResponseEntity.ok(especialidadService.actualizar(id, request));
    }

    // ELIMINAR (lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
