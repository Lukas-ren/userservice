package com.fitting.userservice.service;

import com.fitting.userservice.dto.UserRequest;
import com.fitting.userservice.dto.UserResponse;
import com.fitting.userservice.entity.UserRole;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    List<UserResponse> findAll();

    List<UserResponse> findByRole(UserRole role);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}