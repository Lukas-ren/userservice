package com.fitting.userservice.controller;

import com.fitting.userservice.dto.UserRequest;
import com.fitting.userservice.dto.UserResponse;
import com.fitting.userservice.entity.UserRole;
import com.fitting.userservice.service.UserService;
import com.fitting.userservice.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(
            @Valid @RequestBody UserRequest request) {
        log.info("POST /api/v1/users — email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario creado",
                        userService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        log.info("GET /api/v1/users");
        return ResponseEntity.ok(ApiResponse.ok("Lista de usuarios",
                userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/v1/users/{}", id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario encontrado",
                userService.findById(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> findByEmail(@PathVariable String email) {
        log.info("GET /api/v1/users/email/{}", email);
        return ResponseEntity.ok(ApiResponse.ok("Usuario encontrado",
                userService.findByEmail(email)));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> findByRole(
            @PathVariable UserRole role) {
        log.info("GET /api/v1/users/role/{}", role);
        return ResponseEntity.ok(ApiResponse.ok("Usuarios por rol",
                userService.findByRole(role)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        log.info("PUT /api/v1/users/{}", id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado",
                userService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/users/{}", id);
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado", null));
    }
}