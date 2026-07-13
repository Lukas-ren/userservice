package com.fitting.userservice.dto;

import com.fitting.userservice.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para crear o actualizar un usuario")
public class UserRequest {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String name;

    @Schema(description = "Email del usuario", example = "juan@fitting.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    @Size(max = 150)
    private String email;

    @Schema(description = "Contraseña del usuario (mínimo 8 caracteres)", example = "password123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255)
    private String password;

    @Schema(description = "Rol del usuario", example = "CUSTOMER",
            allowableValues = {"ADMIN", "CUSTOMER", "STAFF"})
    @NotNull(message = "El rol es obligatorio")
    private UserRole role;
}