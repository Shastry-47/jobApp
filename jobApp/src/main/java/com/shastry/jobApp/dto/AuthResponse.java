package com.shastry.jobApp.dto;

import com.shastry.jobApp.model.Role;

public record AuthResponse(String token, Role role, String name) {}
