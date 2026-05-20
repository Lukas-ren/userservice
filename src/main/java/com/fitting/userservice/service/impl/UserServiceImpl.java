package com.fitting.userservice.service.impl;

import com.fitting.userservice.dto.UserRequest;
import com.fitting.userservice.dto.UserResponse;
import com.fitting.userservice.entity.User;
import com.fitting.userservice.entity.UserRole;
import com.fitting.userservice.exception.BusinessException;
import com.fitting.userservice.exception.ResourceNotFoundException;
import com.fitting.userservice.repository.UserRepository;
import com.fitting.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        log.info("Creando usuario con email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    "Ya existe un usuario con el email: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashPassword(request.getPassword()))
                .role(request.getRole())
                .build();

        User saved = userRepository.save(user);
        log.info("Usuario creado con ID: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        log.debug("Buscando usuario con ID: {}", id);
        return toResponse(getUserOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        log.debug("Buscando usuario con email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario con email " + email + " no encontrado"));
        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        log.debug("Listando todos los usuarios");
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findByRole(UserRole role) {
        log.debug("Buscando usuarios con rol: {}", role);
        return userRepository.findByRole(role).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        log.info("Actualizando usuario con ID: {}", id);

        User user = getUserOrThrow(id);

        // Verificar email duplicado ignorando el mismo usuario
        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException(
                            "Ya existe otro usuario con el email: " + request.getEmail());
                });

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        user.setRole(request.getRole());

        User updated = userRepository.save(user);
        log.info("Usuario actualizado: {}", updated.getId());
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        userRepository.deleteById(id);
        log.info("Usuario eliminado: {}", id);
    }

    // ── Hash de contraseña ──────────────────────────────────────────────────────
    // En producción usar BCryptPasswordEncoder de Spring Security
    private String hashPassword(String rawPassword) {
        return Integer.toHexString(rawPassword.hashCode());
    }

    // ── Helper repository ───────────────────────────────────────────────────────

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }

    // ── Mapper interno ──────────────────────────────────────────────────────────

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}