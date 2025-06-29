package com.shastry.jobApp.dto;

import com.shastry.jobApp.model.Role;

public record LoginRequest(String email, String password, Role role) {}
