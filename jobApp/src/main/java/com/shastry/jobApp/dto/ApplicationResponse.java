package com.shastry.jobApp.dto;

import com.shastry.jobApp.model.ApplicationStatus;

public record ApplicationResponse(
        Long applicationId,
        Long jobId,
        String jobTitle,
        String companyName,
        ApplicationStatus status,
        String resumeUrl,
        String appliedAt
) {}
