package com.shastry.jobApp.dto;

import com.shastry.jobApp.model.Role;

public record RegisterRequest(String name, String email, String password, Role role) {}

