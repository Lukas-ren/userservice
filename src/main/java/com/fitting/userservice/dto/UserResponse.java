package com.fitting.userservice.dto;

import com.fitting.userservice.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos del usuario retornados por la API")
public class UserResponse {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    @Schema(description = "Email del usuario", example = "juan@fitting.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "CUSTOMER")
    private UserRole role;

    @Schema(description = "Fecha de creación", example = "2026-05-22T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización", example = "2026-05-22T10:30:00")
    private LocalDateTime updatedAt;
}