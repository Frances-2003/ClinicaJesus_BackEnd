package com.edu.application.service.impl;


import com.edu.application.mapper.DoctorMapper;
import com.edu.application.service.DoctorService;
import com.edu.domain.model.DoctorEntity;
import com.edu.domain.model.EspecialidadEntiity;
import com.edu.domain.model.UsuarioEntity;
import com.edu.domain.recurs.rolUsuario;
import com.edu.domain.repository.DoctorRepository;
import com.edu.domain.repository.EspecialidadRepository;
import com.edu.domain.repository.UsuarioRepository;
import com.edu.web.dto.doctor.DoctorRequest;
import com.edu.web.dto.doctor.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadRepository especialidadRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorResponse crear(DoctorRequest request) {

        // 1. Buscar usuario
        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.usuarioId()));

        // 2. Validar que el usuario tenga rol DOCTOR
        if (usuario.getRol() != rolUsuario.DOCTOR) {
            throw new RuntimeException("El usuario no tiene rol DOCTOR");
        }

        // 3. Validar que el usuario no esté ya registrado como doctor
        doctorRepository.findByUsuarioId(usuario.getId())
                .ifPresent(d -> {
                    throw new RuntimeException("El usuario ya está registrado como doctor");
                });

        // 4. Buscar especialidad
        EspecialidadEntiity especialidad = especialidadRepository.findById(request.especialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + request.especialidadId()));

        // 5. Mapear a entidad
        DoctorEntity doctor = doctorMapper.toEntity(request, usuario, especialidad);

        // 6. Guardar
        DoctorEntity guardado = doctorRepository.save(doctor);

        return doctorMapper.toDto(guardado);
    }

    @Override
    public List<DoctorResponse> listar() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorResponse> listar1() {
        return doctorRepository.findByActivoTrue().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorResponse> listarPorEspecialidad(Long especialidadId) {
        return doctorRepository.findByEspecialidadIdAndActivoTrue(especialidadId)
                .stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public DoctorResponse obtenerPorId(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con id: " + id));

        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorResponse actualizar(Long id, DoctorRequest request) {

        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con id: " + id));

        // Cambiar especialidad si viene en el request
        if (request.especialidadId() != null) {
            EspecialidadEntiity especialidad = especialidadRepository.findById(request.especialidadId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + request.especialidadId()));
            doctor.setEspecialidad(especialidad);
        }

        // Cambiar CMP si viene en el request
        if (request.numeroCmp() != null) {
            doctor.setNumeroCmp(request.numeroCmp());
        }

        // Cambiar estado activo si viene en el request
        if (request.activo() != null) {
            doctor.setActivo(request.activo());
        }

        DoctorEntity actualizado = doctorRepository.save(doctor);
        return doctorMapper.toDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con id: " + id));

        // Eliminación lógica
        doctor.setActivo(false);
        doctorRepository.save(doctor);
    }
}
