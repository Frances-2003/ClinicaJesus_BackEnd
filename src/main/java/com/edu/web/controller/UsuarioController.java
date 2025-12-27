package com.edu.web.controller;


import com.edu.application.service.UsuarioService;
import com.edu.web.dto.usuario.CambiarRolRequest;
import com.edu.web.dto.usuario.UsuarioRequest;
import com.edu.web.dto.usuario.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // REGISTRO (CREAR USUARIO PACIENTE)
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.registrar(request));
    }

    // LISTAR TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // OBTENER USUARIO POR ID
    @GetMapping("/{id:\\\\d+}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequest request
    ) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    // ELIMINAR (LÓGICO: activo = false)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponse> cambiarRol(
            @PathVariable Long id,
            @RequestBody CambiarRolRequest request
    ) {
        return ResponseEntity.ok(usuarioService.cambiarRol(id, request.rol()));
    }
}
