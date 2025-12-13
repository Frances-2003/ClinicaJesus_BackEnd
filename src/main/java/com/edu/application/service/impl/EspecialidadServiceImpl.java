package com.edu.application.service.impl;

import com.edu.application.mapper.EspecialidadMapper;
import com.edu.application.service.EspecialidadService;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.domain.repository.EspecialidadRepository;
import com.edu.web.dto.especialidad.EspecialidadRequest;
import com.edu.web.dto.especialidad.EspecialidadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper especialidadMapper;

    @Override
    public EspecialidadResponse crear(EspecialidadRequest request) {
        // Validar nombre duplicado (ignora mayúsculas/minúsculas)
        if (especialidadRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new RuntimeException("Ya existe una especialidad con el nombre: " + request.nombre());
        }

        EspecialidadEntiity especialidad = especialidadMapper.toEntity(request);
        EspecialidadEntiity guardado = especialidadRepository.save(especialidad);
        return especialidadMapper.toDto(guardado);
    }

    @Override
    public List<EspecialidadResponse> listar() {
        return especialidadRepository.findAll()
                .stream()
                .map(especialidadMapper::toDto)
                .toList();
    }

    @Override
    public List<EspecialidadResponse> listarActivas() {
        return especialidadRepository.findByActivoTrue()
                .stream()
                .map(especialidadMapper::toDto)
                .toList();
    }

    @Override
    public EspecialidadResponse obtenerPorId(Long id) {
        EspecialidadEntiity especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));

        return especialidadMapper.toDto(especialidad);
    }

    @Override
    public EspecialidadResponse actualizar(Long id, EspecialidadRequest request) {
        EspecialidadEntiity especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));

        // Si el nombre cambia, validar que no exista otra especialidad con ese nombre
        String nuevoNombre = request.nombre();
        if (nuevoNombre != null && !nuevoNombre.equalsIgnoreCase(especialidad.getNombre())) {
            if (especialidadRepository.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new RuntimeException("Ya existe una especialidad con el nombre: " + nuevoNombre);
            }
            especialidad.setNombre(nuevoNombre);
        }

        if (request.precioConsulta() != null) {
            especialidad.setPrecioConsulta(request.precioConsulta());
        }

        if (request.activo() != null) {
            especialidad.setActivo(request.activo());
        }

        EspecialidadEntiity actualizado = especialidadRepository.save(especialidad);
        return especialidadMapper.toDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        EspecialidadEntiity especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));

        // Eliminación lógica: inactivar en vez de borrar
        especialidad.setActivo(false);
        especialidadRepository.save(especialidad);
    }
}
