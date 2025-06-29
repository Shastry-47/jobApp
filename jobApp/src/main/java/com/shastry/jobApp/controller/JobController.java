package com.shastry.jobApp.controller;

import com.shastry.jobApp.dto.JobRequest;
import com.shastry.jobApp.dto.JobResponse;
import com.shastry.jobApp.model.Job;
import com.shastry.jobApp.model.Role;
import com.shastry.jobApp.model.User;
import com.shastry.jobApp.service.JobService;
import com.shastry.jobApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final UserService userService;

    @PreAuthorize("hasRole('RECRUITER')")
    @PostMapping
    public ResponseEntity<JobResponse> postJob(@Valid @RequestBody JobRequest jobRequest, Authentication auth) {
        String email = auth.getName();
        User recruiter = (User) userService.findByEmail(email).orElseThrow();

        System.out.println("this is posting jobc controller");

        Job job = Job.builder()
                .companyName(jobRequest.companyName())
                .companyDescription(jobRequest.companyDescription())
                .jobTitle(jobRequest.jobTitle())
                .jobDescription(jobRequest.jobDescription())
                .salary(jobRequest.salary())
                .experience(jobRequest.experience())
                .location(jobRequest.location())
                .recruiter(recruiter)
                .build();

        Job saved = jobService.save(job);

        JobResponse jobResponse = new JobResponse(
                saved.getJobId(),
                saved.getCompanyName(),
                saved.getCompanyDescription(),
                saved.getJobTitle(),
                saved.getJobDescription(),
                saved.getSalary(),
                saved.getExperience(),
                saved.getLocation(),
                recruiter.getName()
        );

        return ResponseEntity.ok(jobResponse);
    }

    @PreAuthorize("hasRole('APPLICANT')")
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();

        List<JobResponse> responses = jobs.stream().map(job -> new JobResponse(
                job.getJobId(),
                job.getCompanyName(),
                job.getCompanyDescription(),
                job.getJobTitle(),
                job.getJobDescription(),
                job.getSalary(),
                job.getExperience(),
                job.getLocation(),
                job.getRecruiter().getName()
        )).toList();

        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/recruiter")
    public ResponseEntity<List<JobResponse>> getJobsByRecruiter(Authentication auth) {
        String email = auth.getName();
        User recruiter = (User) userService.findByEmail(email).orElseThrow();

        List<Job> jobs = jobService.getJobsByRecruiter(recruiter.getId());

        List<JobResponse> responses = jobs.stream().map(job -> new JobResponse(
                job.getJobId(),
                job.getCompanyName(),
                job.getCompanyDescription(),
                job.getJobTitle(),
                job.getJobDescription(),
                job.getSalary(),
                job.getExperience(),
                job.getLocation(),
                recruiter.getName()
        )).toList();

        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('APPLICANT')")
    @GetMapping("/search")
    public ResponseEntity<Page<JobResponse>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "jobId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Job> jobPage = jobService.searchJobs(title, location, companyName, minSalary, minExperience, pageable);

        Page<JobResponse> responsePage = jobPage.map(job -> new JobResponse(
                job.getJobId(),
                job.getCompanyName(),
                job.getCompanyDescription(),
                job.getJobTitle(),
                job.getJobDescription(),
                job.getSalary(),
                job.getExperience(),
                job.getLocation(),
                job.getRecruiter().getName()
        ));

        return ResponseEntity.ok(responsePage);
    }
}
