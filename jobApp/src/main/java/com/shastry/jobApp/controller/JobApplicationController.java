    package com.shastry.jobApp.controller;

    import com.shastry.jobApp.dto.ApplicationRequest;
    import com.shastry.jobApp.dto.ApplicationResponse;
    import com.shastry.jobApp.dto.JobApplicationResponse;
    import com.shastry.jobApp.model.ApplicationStatus;
    import com.shastry.jobApp.model.Job;
    import com.shastry.jobApp.model.JobApplication;
    import com.shastry.jobApp.model.User;
    import com.shastry.jobApp.service.JobApplicationService;
    import com.shastry.jobApp.service.JobService;
    import com.shastry.jobApp.exceptions.UnauthorizedException;
    import com.shastry.jobApp.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.server.ResponseStatusException;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/applications")
    public class JobApplicationController {

        private final JobApplicationService jobApplicationService;
        private final UserService userService;
        private final JobService jobService;

        @PostMapping
        public ResponseEntity<ApplicationResponse> applyToJob(@RequestBody ApplicationRequest req, Authentication auth) {

            String email = auth.getName();
            User applicant = userService.findByEmail(email).orElseThrow();
            Job job = jobService.findById(req.jobId()).orElseThrow();

            System.out.println("this is application controller");

            JobApplication application = JobApplication.builder()
                    .applicant(applicant)
                    .job(job)
                    .resumeUrl(req.resumeUrl())
                    .appliedAt(LocalDateTime.now())
                    .build();

            JobApplication saved = jobApplicationService.save(application);

            ApplicationResponse response = new ApplicationResponse(
                    saved.getId(),
                    job.getJobId(),
                    job.getJobTitle(),
                    job.getCompanyName(),
                    saved.getStatus(),
                    saved.getResumeUrl(),
                    saved.getAppliedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            );

            return ResponseEntity.ok(response);
        }


        @GetMapping("/applicant")
        public ResponseEntity<List<ApplicationResponse>> getMyApplications(Authentication auth) {
            String email = auth.getName();
            User applicant = (User) userService.findByEmail(email).orElseThrow();

            List<JobApplication> applications = jobApplicationService.getApplicationsByApplicant(applicant.getId());

            List<ApplicationResponse> responses = applications.stream().map(app -> new ApplicationResponse(
                    app.getId(),
                    app.getJob().getJobId(),
                    app.getJob().getJobTitle(),
                    app.getJob().getCompanyName(),
                    app.getStatus(),
                    app.getResumeUrl(),
                    app.getAppliedAt() != null
                            ? app.getAppliedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                            : "N/A"
            )).toList();

            return ResponseEntity.ok(responses);
        }

        @GetMapping("/job/{jobId}")
        public ResponseEntity<List<JobApplicationResponse>> getApplicationsByJob(
                @PathVariable Long jobId,
                Authentication auth) {

            String email = auth.getName();
            User recruiter = userService.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Recruiter not found"));

            Job job = jobService.findById(jobId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));

            if (!job.getRecruiter().getId().equals(recruiter.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view applications for this job");
            }

            List<JobApplication> applications = jobApplicationService.getApplicationsByJob(jobId);

            List<JobApplicationResponse> responses = applications.stream().map(app -> new JobApplicationResponse(
                    app.getId(),
                    app.getApplicant().getId(),
                    app.getApplicant().getName(),
                    app.getApplicant().getEmail(),
                    app.getStatus(),
                    app.getResumeUrl(),
                    app.getAppliedAt()
            )).toList();

            return ResponseEntity.ok(responses);
        }


        @PutMapping("/{applicationId}/status")
        public ResponseEntity<Void> updateApplicationStatus(
                @PathVariable Long applicationId,
                @RequestParam ApplicationStatus status,
                Authentication auth) throws UnauthorizedException {

            String email = auth.getName();
            User recruiter = (User) userService.findByEmail(email).orElseThrow();

            jobApplicationService.updateStatus(applicationId, status, recruiter);
            return ResponseEntity.ok().build();
        }
    }
