package com.shastry.jobApp.dto;

public record JobRequest(
        String companyName,
        String companyDescription,
        String jobTitle,
        String jobDescription,
        double salary,
        int experience,
        String location
) {}
