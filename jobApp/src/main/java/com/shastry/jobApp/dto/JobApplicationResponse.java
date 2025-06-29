package com.shastry.jobApp.dto;

import com.shastry.jobApp.model.ApplicationStatus;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long applicationId,
        Long applicantId,
        String applicantName,
        String applicantEmail,
        ApplicationStatus status,
        String resumeUrl,
        LocalDateTime appliedAt
) {}

