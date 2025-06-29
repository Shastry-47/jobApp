package com.shastry.jobApp.dto;

public record JobResponse(
        Long jobId,
        String companyName,
        String companyDescription,
        String jobTitle,
        String jobDescription,
        double salary,
        int experience,
        String location,
        String recruiterName
) {}
