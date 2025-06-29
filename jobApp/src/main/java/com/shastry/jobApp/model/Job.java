package com.shastry.jobApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;


@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @Size(max = 500 , message = "Description must be at most of 500 characters")
    private String companyDescription;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Job description cannot be blank")
    private String jobDescription;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "salary myst be at least 0")
    private double salary;

    @Min(value = 0 , message = "experience must be at least 0 years")
    private int experience;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private User recruiter;

}

