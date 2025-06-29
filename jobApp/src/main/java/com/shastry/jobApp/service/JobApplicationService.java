package com.shastry.jobApp.service;


import com.shastry.jobApp.exceptions.UnauthorizedException;
import com.shastry.jobApp.model.ApplicationStatus;
import com.shastry.jobApp.model.JobApplication;
import com.shastry.jobApp.model.User;
import com.shastry.jobApp.repo.JobApplicationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepo jobApplicationRepo;

    public JobApplication save(JobApplication application) {
        System.out.println("this is applications service ");
        return jobApplicationRepo.save(application);
    }

    public List<JobApplication> getApplicationsByApplicant(Long id) {
        return jobApplicationRepo.findByApplicantId(id);
    }

    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return jobApplicationRepo.findAllById(Collections.singleton(jobId));
    }

    public void updateStatus(Long applicationId, ApplicationStatus status, User recruiter) throws UnauthorizedException {
        JobApplication application = jobApplicationRepo.findById(applicationId)
                .orElseThrow();

        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
            throw new UnauthorizedException("You are not authorized to update this application.");
        }

        application.setStatus(status);
        jobApplicationRepo.save(application);
    }

}
